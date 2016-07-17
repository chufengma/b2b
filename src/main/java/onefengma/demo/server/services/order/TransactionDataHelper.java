package onefengma.demo.server.services.order;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.util.List;

/**
 * Created by chufengma on 16/6/18.
 */
public class TransactionDataHelper extends BaseDataHelper {

    private static TransactionDataHelper instance;

    public static TransactionDataHelper instance() {
        if (instance == null) {
            instance = new TransactionDataHelper();
        }
        return instance;
    }

    public void insertIronBuyTransaction(Connection conn, String sellerId, String productId, float money, float count) {
        insertTransaction(conn, sellerId, productId, 0, money, count);
    }

    public void insertHandingBuyTransaction(Connection conn, String sellerId, String productId, float money, float count) {
        insertTransaction(conn, sellerId, productId, 1, money, count);
    }

    public void insertOrderTransaction(Connection conn, String orderId) {
        String proSql = "select * from product_orders where id=:id";
        List<Row> rows = conn.createQuery(proSql).addParameter("id", orderId).executeAndFetchTable().rows();
        if (!rows.isEmpty()) {
            Row row = rows.get(0);
            String sellerId = row.getString("sellerId");
            String proId = row.getString("productId");
            float count = row.getFloat("count");
            int proType = row.getInteger("productType");
            float price = proType == 0 ? IronDataHelper.getIronDataHelper().getIronBuySupplyPrice(proId)
                    : HandingDataHelper.getHandingDataHelper().getHandingBuySupplyPrice(proId);
            insertTransaction(conn, sellerId, orderId, 2, price * count, count);
        }
    }

    private void insertTransaction(Connection conn, String sellerId, String productId, int productType, float money, float count) {
        String sql = "insert into seller_transactions " +
                "set sellerId=:sellerId,productId=:productId,productType=:productType,money=:money,count=:count,finishTime=:finishTime";
        conn.createQuery(sql).addParameter("sellerId", sellerId)
                .addParameter("productId", productId)
                .addParameter("productType", productType)
                .addParameter("money", money)
                .addParameter("count", count)
                .addParameter("finishTime", System.currentTimeMillis()).executeUpdate();
    }

}