package onefengma.demo.server.services.products;

import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.product.HandingBuy;
import onefengma.demo.server.model.product.HandingProduct;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingDataHelper extends BaseDataHelper {

    private static HandingDataHelper handingDataHelper;

    public static HandingDataHelper getHandingDataHelper() {
        if (handingDataHelper == null) {
            handingDataHelper = new HandingDataHelper();
        }
        return handingDataHelper;
    }

    public int getMaxCount() {
        return getMaxCounts("handing_product");
    }

    public void insertHandingProduct(HandingProduct handingProduct) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "handing_product", handingProduct)
                    .executeUpdate();
        }
    }

    public List<HandingProduct> getHandingProducts(PageBuilder pageBuilder) {
        String sql = "select * from handing_product " + (pageBuilder.hasWhere() ? " where " : "") + pageBuilder.generateSql();
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(HandingProduct.class);
        }
    }

    public void pushHandingBuy(HandingBuy handingBuy) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "handing_buy", handingBuy).executeUpdate();
        }
    }

    public List<HandingProduct> searchHandingProduct(String keyword, PageBuilder pageBuilder) {
        keyword = "%" + keyword + "%";
        String sql = "select * from handing_product " +
                "where title like \"" + keyword + "\"" +
                "or type like \"" + keyword + "\"" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(HandingProduct.class);
        }
    }

    public int searchHandingProductsMaxCount(String keyword) {
        keyword = "%" + keyword + "%";
        String sql = "select count(*) from handing_product " +
                "where title like \"" + keyword + "\"" +
                "or type like \"" + keyword + "\"";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

}
