package onefengma.demo.server.services.order;

import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.order.MyOrdersResponse;
import onefengma.demo.server.model.order.OrderBrief;
import onefengma.demo.server.services.funcs.CityDataHelper;
import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.apibeans.LastRecords;
import onefengma.demo.server.model.order.Order;
import onefengma.demo.server.model.order.OrderDynamic;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

/**
 * Created by chufengma on 16/6/18.
 */
public class OrderDataHelper extends BaseDataHelper {

    private static OrderDataHelper orderDataHelper;
    private static LastRecords lastRecords;
    private static long lastGetTime;

    public static OrderDataHelper instance() {
        if (orderDataHelper == null) {
            orderDataHelper = new OrderDataHelper();
        }
        return orderDataHelper;
    }

    public LastRecords getLastRecords() {
        if (DateHelper.isToday(lastGetTime) && lastRecords != null) {
            return lastRecords;
        }

        lastRecords = new LastRecords();
        String weightSql = "select sum(ironCount) from product_orders where productType=0 and sellTime >= :lastTime and sellTime < :nextTime";
        String countSql = "select count(*) from product_orders where  productType=0 and sellTime >= :lastTime and sellTime < :nextTime";
        String moneySql = " select sum((ironCount*ironPrice)) as sellMoney from product_orders where productType=0 and sellTime >= :lastTime and sellTime < :nextTime";
        try(Connection connection = getConn()) {
            float weight = connection.createQuery(weightSql)
                    .addParameter("lastTime", DateHelper.getLastDayTimestamp())
                    .addParameter("nextTime", DateHelper.getNextDayTimestamp())
                    .executeScalar(Float.class);
            lastRecords.weight = weight;

            float count = connection.createQuery(countSql)
                    .addParameter("lastTime", DateHelper.getLastDayTimestamp())
                    .addParameter("nextTime", DateHelper.getNextDayTimestamp())
                    .executeScalar(Float.class);
            lastRecords.count = count;

            float sellMoney = connection.createQuery(moneySql)
                    .addParameter("lastTime", DateHelper.getLastDayTimestamp())
                    .addParameter("nextTime", DateHelper.getNextDayTimestamp())
                    .executeScalar(Float.class);
            lastRecords.sellingMoney = sellMoney;
        }

        lastGetTime = System.currentTimeMillis();
        return lastRecords;
    }

    public int getOrderStatus(String orderId) {
        String sql = "select status from product_orders where id=:id";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", orderId).executeScalar(Integer.class);
        }
    }

    public String getBuyerId(String orderId) {
        String sql = "select buyerId from product_orders where id=:id";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", orderId).executeScalar(String.class);
        }
    }

    public void deleteOrder(String orderId) {
        String sql = "delete from product_orders where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", orderId).executeUpdate();
        }
    }

    public void voteOrder(String orderId, float vote) {
        String sql = "update product_orders set singleScore=:score,status=2 where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("score", vote).addParameter("id", orderId).executeUpdate();
        }
    }

    public MyOrdersResponse getMyOrders(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        MyOrdersResponse myOrdersResponse = new MyOrdersResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        String dataSql = "select * from product_orders where buyerId=:userId order by status asc " + pageBuilder.generateLimit();
        String waitForConfirmSql = "select count(*) from product_orders where buyerId=:userId and status = 1";
        String waitForVoteSql = "select count(*) from product_orders where buyerId=:userId and status = 2";
        String countSql = "select count(*) from product_orders where buyerId=:userId order by status asc ";
        try(Connection conn = getConn()) {
            Table table = conn.createQuery(dataSql).addParameter("userId", userId).executeAndFetchTable();
            List<OrderBrief> orderBriefs = new ArrayList<>();
            for(Row row : table.rows()) {
                orderBriefs.add(getOrderBrief(conn, row));
            }
            myOrdersResponse.orders = orderBriefs;
            myOrdersResponse.maxCount = conn.createQuery(countSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOrdersResponse.waitForConfirm = conn.createQuery(waitForConfirmSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOrdersResponse.waitForVote = conn.createQuery(waitForVoteSql).addParameter("userId", userId).executeScalar(Integer.class);
        }
        return myOrdersResponse;
    }

    private OrderBrief getOrderBrief(Connection conn, Row row) throws NoSuchFieldException, IllegalAccessException {
        String ironSql = "select * from iron_product where proId=:proId";
        String handingSql = "select * from handing_product where id=:proId";

        OrderBrief orderBrief = new OrderBrief();
        orderBrief.id = row.getString("id");
        orderBrief.status = row.getInteger("status");
        int productType = row.getInteger("productType");
        orderBrief.sellMoney = row.getFloat("totalMoney");
        orderBrief.sellTime = row.getLong("sellTime");
        orderBrief.timeLimit = row.getLong("timeLimit");
        orderBrief.productType = productType;
        String proId = row.getString("productId");

        if (orderBrief.status != 0) {
            String sellerId = row.getString("sellerId");
            orderBrief.sellerMobile = getSellerMobile(conn, sellerId);
        }

        if (productType == 0) {
            orderBrief.price = row.getFloat("ironPrice");
            Table ironTable = conn.createQuery(ironSql).addParameter("proId", proId).executeAndFetchTable();
            if (ironTable.rows().size() >= 1) {
                Row ironRow = ironTable.rows().get(0);
                orderBrief.cover = ironRow.getString("cover");
                orderBrief.desc = ironRow.getString("material") + " " + ironRow.getString("ironType");
                orderBrief.city = CityDataHelper.instance().getCityDescById(ironRow.getString("sourceCityId"));
            }
        } else {
            Table handingTable = conn.createQuery(handingSql).addParameter("proId", proId).executeAndFetchTable();
            if (handingTable.rows().size() >= 1) {
                Row ironRow = handingTable.rows().get(0);
                orderBrief.price = ironRow.getFloat("price");
                orderBrief.cover = ironRow.getString("cover");
                orderBrief.desc = ironRow.getString("type");
                orderBrief.city = CityDataHelper.instance().getCityDescById(ironRow.getString("souCityId"));
            }
        }
        return orderBrief;
    }

    public String getSellerMobile(Connection conn, String sellerId) {
        String mobileSql = "select mobile from user where userId=:userId";
        return conn.createQuery(mobileSql).addParameter("userId", sellerId).executeScalar(String.class);
    }

    public List<OrderDynamic> getOrdersDynamic() {
        String sql = "select orders.id,ironId,ironType,material,mobile,pushTime,ironCount,price " +
                "from product_orders,user,iron_product " +
                "where sellerId=user.userId and productType=1 and ironId = iron_product.proId " +
                "order by sellTime desc limit 0,10";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(OrderDynamic.class);
        }
    }

    public void translate(Order order) throws Exception {
        String idSql = "select count(id) from product_orders where sellTime >= :lastTime and sellTime < :nextTime";
        String tableName = ((order.productType == 0) ? "iron_product" : "handing_product");
        String proId = ((order.productType == 0) ? tableName + ".proId" : tableName + ".id");
        String sellerIdSql = "select (seller.userId) as sellerId ,salesmanId from " + tableName + ",seller where seller.userId=" + tableName +".userId and " + proId + "=:proId";
        transaction(new Func() {
            @Override
            public void doIt(Connection conn) throws Exception {
                List<OrderSeller> orderSellers = conn.createQuery(sellerIdSql)
                        .addParameter("proId", order.productId)
                        .executeAndFetch(OrderSeller.class);
                int id = conn.createQuery(idSql)
                        .addParameter("lastTime", DateHelper.getLastDayTimestamp())
                        .addParameter("nextTime", DateHelper.getNextDayTimestamp())
                        .executeScalar(Integer.class);
                String dateStr = DateHelper.getDataStrWithOut();
                dateStr = order.productType + dateStr + "0" + id;
                order.id = dateStr;
                order.sellerId = orderSellers.get(0).sellerId;
                order.salesmanId = orderSellers.get(0).salesmanId;
                createInsertQuery(conn, "product_orders", order).executeUpdate();
            }
        });
    }


    public static class OrderSeller {
        public String sellerId;
        public int salesmanId;
    }

}
