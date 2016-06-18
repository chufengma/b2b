package onefengma.demo.server.services.order;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.apibeans.LastRecords;
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
        String weightSql = "select sum(ironCount) from orders where sellTime >= :lastTime and sellTime < :nextTime";
        String countSql = "select count(*) from orders where sellTime >= :lastTime and sellTime < :nextTime";
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
        }

        lastGetTime = System.currentTimeMillis();
        return lastRecords;
    }

    public List<OrderDynamic> getOrdersDynamic() {
        String sql = "select orders.id,ironId,ironType,material,mobile,pushTime,ironCount,price " +
                "from orders,user,iron_product " +
                "where sellerId=user.userId and productType=1 and ironId = iron_product.proId " +
                "order by sellTime desc limit 0,10";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(OrderDynamic.class);
        }
    }

}
