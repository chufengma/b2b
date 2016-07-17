package onefengma.demo.server.services.order;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.order.MyCommingOrdersResponse;
import onefengma.demo.server.model.apibeans.order.MyOrdersResponse;
import onefengma.demo.server.model.apibeans.product.MyCarsResponse;
import onefengma.demo.server.model.apibeans.product.CarProductBrief;
import onefengma.demo.server.model.order.OrderBrief;
import onefengma.demo.server.model.product.HandingProductBrief;
import onefengma.demo.server.model.product.IronProductBrief;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;
import org.sql2o.Connection;

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

    public void voteOrder(String orderId, float vote) {
        String sql = "update product_orders set singleScore=:score,status=2 where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("score", vote).addParameter("id", orderId).executeUpdate();
        }
    }

    public MyOrdersResponse getMyOrders(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        MyOrdersResponse myOrdersResponse = new MyOrdersResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        String dataSql = "select * from product_orders where buyerId=:userId and status <> 4 order by status asc " + pageBuilder.generateLimit();
        String waitForConfirmSql = "select count(*) from product_orders where buyerId=:userId and status = 0";
        String waitForVoteSql = "select count(*) from product_orders where buyerId=:userId and status = 1";
        String countSql = "select count(*) from product_orders where buyerId=:userId and status <> 4 order by status asc ";

        try(Connection conn = getConn()) {
            Table table = conn.createQuery(dataSql).addParameter("userId", userId).executeAndFetchTable();
            List<OrderBrief> orderBriefs = new ArrayList<>();
            for(Row row : table.rows()) {
                OrderBrief orderBrief = getOrderBrief(conn, row, false);
                orderBriefs.add(orderBrief);
            }
            myOrdersResponse.orders = orderBriefs;
            myOrdersResponse.maxCount = conn.createQuery(countSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOrdersResponse.waitForConfirm = conn.createQuery(waitForConfirmSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOrdersResponse.waitForVote = conn.createQuery(waitForVoteSql).addParameter("userId", userId).executeScalar(Integer.class);
        }

        updateOutofDateStatic("", userId);
        updateOutofDateStatic(userId, "");

        return myOrdersResponse;
    }

    private OrderBrief getOrderBrief(Connection conn, Row row, boolean isSeller) throws NoSuchFieldException, IllegalAccessException {
        String ironSql = "select * from iron_product where proId=:proId";
        String handingSql = "select * from handing_product where id=:proId";

        OrderBrief orderBrief = new OrderBrief();
        orderBrief.id = row.getString("id");
        orderBrief.status = row.getInteger("status");
        int productType = row.getInteger("productType");
        orderBrief.sellMoney = row.getFloat("totalMoney");
        orderBrief.count = row.getFloat("count");
        orderBrief.sellTime = row.getLong("sellTime");
        orderBrief.timeLimit = row.getLong("timeLimit");
        orderBrief.productType = productType;
        String proId = row.getString("productId");

        if (isSeller) {
            if (orderBrief.status != 0) {
                String sellerId = row.getString("buyerId");
                orderBrief.buyerMobile = getSellerMobile(conn, sellerId);
            }
        } else {
            if (orderBrief.status != 0) {
                String sellerId = row.getString("sellerId");
                orderBrief.sellerMobile = getSellerMobile(conn, sellerId);
            }
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

    public void updateOutofDateStatic(String sellerId, String buyerId) {
        PageBuilder pageBuilder = new PageBuilder(0, 0);
        pageBuilder.addEqualWhere("sellerId", sellerId);
        pageBuilder.addEqualWhere("buyerId", buyerId);

        String sql = "update product_orders " +
                "set status = 3,cancelBy = 3 where (sellTime+timeLimit) < :currentTime and status = 0 "
                + (pageBuilder.hasWhere() ? " and " : "  ") + pageBuilder.generateWhere();

        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis()).executeUpdate();
        }
    }

    public String getSellerMobile(Connection conn, String sellerId) {
        String mobileSql = "select mobile from user where userId=:userId";
        return conn.createQuery(mobileSql).addParameter("userId", sellerId).executeScalar(String.class);
    }

    public String getSellerUserId(Connection conn, String orderId) {
        String mobileSql = "select sellerId from user where id=:id";
        return conn.createQuery(mobileSql).addParameter("id", orderId).executeScalar(String.class);
    }

    public List<OrderDynamic> getOrdersDynamic() {
        String sql = "select product_orders.id,iron_product.proId,iron_product.proId as ironId,ironType,material,mobile,pushTime,count,price  " +
                "from product_orders, iron_product, user where productType=0 and iron_product.proId = productId and status<>4 and buyerId=user.userId " +
                "order by sellTime desc limit 0,10";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(OrderDynamic.class);
        }
    }

    public void translate(Order order, boolean isFromCar, int carId) throws Exception {
        String idSql = "select count(id) from product_orders where sellTime >= :lastTime and sellTime < :nextTime";
        String tableName = ((order.productType == 0) ? "iron_product" : "handing_product");
        String proId = ((order.productType == 0) ? tableName + ".proId" : tableName + ".id");
        String sellerIdSql = "select userId from " + tableName + " where " + proId + "=:proId";
        String deleteCarSql = "delete from order_car where userId=:userId and proId=:proId and productType=:productType and carId=:carId";
        String selectCarSql = "select count(*) from order_car where userId=:userId and proId=:proId and productType=:productType";

        order.salesmanId = UserDataHelper.instance().getSalesManId(order.buyerId);

        if (order.productType == 0) {
            order.totalMoney = IronDataHelper.getIronDataHelper().getIronPrice(order.productId) * order.count;
        } else {
            order.totalMoney = HandingDataHelper.getHandingDataHelper().getHandingPrice(order.productId) * order.count;
        }

        transaction(conn -> {
            if (isFromCar) {
                Integer count = conn.createQuery(selectCarSql).addParameter("userId", order.buyerId)
                        .addParameter("proId", order.productId)
                        .addParameter("productType", order.productType).executeScalar(Integer.class);
                if (count == null || count == 0) {
                    return;
                }
                conn.createQuery(deleteCarSql).addParameter("userId", order.buyerId)
                        .addParameter("proId", order.productId)
                        .addParameter("carId", carId)
                        .addParameter("productType", order.productType).executeUpdate();
            }

            String sellerId = conn.createQuery(sellerIdSql)
                    .addParameter("proId", order.productId).executeScalar(String.class);

            int id = conn.createQuery(idSql)
                    .addParameter("lastTime", DateHelper.getLastDayTimestamp())
                    .addParameter("nextTime", DateHelper.getNextDayTimestamp())
                    .executeScalar(Integer.class);
            String dateStr = DateHelper.getDataStrWithOut();
            dateStr = order.productType + dateStr + "0" + id;
            order.id = dateStr;
            order.sellerId = sellerId;

            createInsertQuery(conn, "product_orders", order).executeUpdate();
        });
    }


    public boolean isOrderUserRight(String userId, String orderId) {
        String sql = "select buyerId from product_orders where id=:orderId";
        try(Connection conn = getConn()) {
            String buyerId = conn.createQuery(sql).addParameter("orderId", orderId).executeScalar(String.class);
            return StringUtils.equals(buyerId, userId);
        }
    }


    public void addToCar(String userId, String proId, int productType) {
        String sql = "insert into order_car set userId=:userId,proId=:proId,productType=:productType";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId)
                    .addParameter("proId", proId)
                    .addParameter("productType", productType)
                    .executeUpdate();
        }
    }

    public MyCarsResponse getMyCars(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select * from order_car where " + pageBuilder.generateSql(true);
        String countSql = "select count(*) from order_car where " + pageBuilder.generateSql(false);
        String ironSql = "select " + generateFiledStringExclude(IronProductBrief.class, "monthSellCount") + " from iron_product where proId=:id";
        String handingSql = "select " + generateFiledStringExclude(HandingProductBrief.class, "monthSellCount") + " from handing_product where id=:id";

        MyCarsResponse myCarsResponse = new MyCarsResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            List<CarProductBrief> productBriefs = new ArrayList<>();
            for(Row row : rows) {
                CarProductBrief brief = new CarProductBrief();
                brief.proId = row.getString("proId");
                brief.productType = row.getInteger("productType");
                brief.carId = row.getInteger("id");
                if (brief.productType == 0) {
                    IronProductBrief ironProductBrief = conn.createQuery(ironSql).addParameter("id", brief.proId).executeAndFetchFirst(IronProductBrief.class);
                    if (ironProductBrief != null) {
                        brief.cover = ironProductBrief.cover;
                        brief.price = ironProductBrief.price;
                        brief.sourceCity = CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId);
                        brief.desc = ironProductBrief.material + " " + ironProductBrief.ironType;
                    }
                } else if (brief.productType == 1) {
                    HandingProductBrief handingProductBrief = conn.createQuery(handingSql).addParameter("id", brief.proId).executeAndFetchFirst(HandingProductBrief.class);
                    if (handingProductBrief != null) {
                        brief.cover = handingProductBrief.cover;
                        brief.price = handingProductBrief.price;
                        brief.sourceCity = CityDataHelper.instance().getCityDescById(handingProductBrief.souCityId);
                        brief.desc = handingProductBrief.type;
                    }
                }
                productBriefs.add(brief);
            }

            myCarsResponse.cars = productBriefs;
            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;
            myCarsResponse.maxCount = count;

            return myCarsResponse;
        }
    }

    public void deleteCar(int carId, String userId) {
        String sql = "delete from order_car where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", carId).executeUpdate();
        }
    }

    public boolean isCarIsUser(int carId, String userId) {
        String sql = "select userId from order_car where id=:id";
        try(Connection conn = getConn()) {
            String userIdQuery = conn.createQuery(sql).addParameter("id", carId).executeScalar(String.class);
            return StringUtils.equals(userIdQuery, userId);
        }
    }

    public int getCarCount(String userId) {
        String sql = "select count(*) from order_car where userId=:userId";
        try(Connection conn = getConn()) {
            Integer integer = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return integer == null ? 0 : integer;
        }
    }

    public MyCommingOrdersResponse getCommingOrders(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        MyCommingOrdersResponse myOrdersResponse = new MyCommingOrdersResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        String dataSql = "select * from product_orders where sellerId=:userId and status <> 4 order by status asc " + pageBuilder.generateLimit();
        String waitForConfirmSql = "select count(*) from product_orders where sellerId=:userId and status = 0";
        String countSql = "select count(*) from product_orders where sellerId=:userId and status <> 4 order by status asc ";
        try(Connection conn = getConn()) {
            Table table = conn.createQuery(dataSql).addParameter("userId", userId).executeAndFetchTable();
            List<OrderBrief> orderBriefs = new ArrayList<>();
            for(Row row : table.rows()) {
                OrderBrief orderBrief = getOrderBrief(conn, row, true);
                orderBrief.score = row.getFloat("singleScore");
                orderBriefs.add(orderBrief);
            }
            myOrdersResponse.orders = orderBriefs;
            myOrdersResponse.maxCount = conn.createQuery(countSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOrdersResponse.waitForConfirm = conn.createQuery(waitForConfirmSql).addParameter("userId", userId).executeScalar(Integer.class);
        }
        return myOrdersResponse;
    }

    public boolean isOrderSellerRight(String orderId, String sellerId) {
        String sql = "select sellerId from product_orders where id=:orderId";
        try(Connection conn = getConn()) {
            String querySeller = conn.createQuery(sql).addParameter("orderId", orderId).executeScalar(String.class);
            return StringUtils.equals(querySeller, sellerId);
        }
    }

    public boolean isOrderExited(String orderId) {
        String sql = "select count(*) from product_orders where id=:orderId";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("orderId", orderId).executeScalar(Integer.class);
            return count != null && count > 0;
        }
    }

    public void confirmOrder(String orderId) throws Exception {
        String sql = "update product_orders set status = 1, finishTime=:finishTime where id=:orderId and status=0";

        transaction((conn)-> {
            conn.createQuery(sql)
                    .addParameter("finishTime", System.currentTimeMillis())
                    .addParameter("orderId", orderId)
                    .executeUpdate();
            // 添加记录
            TransactionDataHelper.instance().insertOrderTransaction(conn, orderId);
            // 添加积分
            addBuyerIntegralByOrder(orderId);
        });
    }

    public void addBuyerIntegralByOrder(String orderId) {
        String sql = "select * from product_orders where id=:orderId";
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).addParameter("orderId", orderId).executeAndFetchTable().rows();
            if (!rows.isEmpty()) {
                Row row = rows.get(0);
                String buyerId = row.getString("buyerId");
                String sellerId = row.getString("sellerId");
                float totalMoney = row.getFloat("totalMoney");
                addIntegralByBuy(conn, buyerId, sellerId, totalMoney);
            }
        }
    }

    public void addIntegralByBuy(Connection conn , String buyerId, String sellerId, float totalMoney) {
        String buyerIntegralSql = "update user set integral = (integral + :add) where userId=:userId";
        String sellerIntegralSql = "update seller set integral = (integral + :add) where userId=:userId";

        float buyerIntegral = (long)((long)totalMoney / 1000) * 0.5f;
        float sellerIntegral = (long)((long)totalMoney / 1000) * 0.1f;

        conn.createQuery(buyerIntegralSql)
                .addParameter("userId", buyerId)
                .addParameter("add", buyerIntegral)
                .executeUpdate();

        conn.createQuery(sellerIntegralSql)
                .addParameter("userId", buyerId)
                .addParameter("add", sellerIntegral)
                .executeUpdate();
    }

    public void concelOrder(String orderId) {
        String sql = "update product_orders set status=3,cancelBy=2 where id=:orderId and status=0";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("orderId", orderId).executeUpdate();
        }
    }

    public void deleteOrder(String orderId, boolean isFromSeller) {
        deleteOrderInner(orderId, isFromSeller ? 2 : 1);
    }

    public void deleteOrderBuyAdmin(String orderId) {
        deleteOrderInner(orderId, 3);
    }

    private void deleteOrderInner(String orderId, int from) {
        String sellerSql = "update product_orders set status=4,deleteBy=" + from + " where id=:orderId and (status=1 or status=2)";
        try(Connection conn = getConn()) {
            conn.createQuery(sellerSql).addParameter("orderId", orderId).executeUpdate();
        }
    }

    public boolean isProductInOrdering(String proId, int productType) {
        String sql = "select count(*) from product_orders where productType=:productType and productId=:proId and status=0";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql)
                    .addParameter("productType", productType)
                    .addParameter("proId", proId)
                    .executeScalar(Integer.class);

            return count != null && count != 0;
        }
    }

    public static class OrderSeller {
        public String sellerId;
        public int salesmanId;
    }

}
