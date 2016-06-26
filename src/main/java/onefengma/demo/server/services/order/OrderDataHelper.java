package onefengma.demo.server.services.order;

import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.apibeans.LastRecords;
import onefengma.demo.server.model.order.Order;
import onefengma.demo.server.model.order.OrderDynamic;

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
