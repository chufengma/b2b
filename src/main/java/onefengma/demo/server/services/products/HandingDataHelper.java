package onefengma.demo.server.services.products;

import org.sql2o.Connection;
import org.sql2o.Query;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.product.HandingProduct;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingDataHelper extends BaseDataHelper {

    private static HandingDataHelper handingDataHelper;

    public static HandingDataHelper getHandingDataHelper () {
        if (handingDataHelper == null) {
            handingDataHelper = new HandingDataHelper();
        }
        return handingDataHelper;
    }

    public int getMaxCount() {
        return getMaxCounts("handing_product");
    }

    public void insertHandingProduct(HandingProduct handingProduct) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try(Connection conn = getConn()) {
            createInsertQuery(conn, "handing_product", handingProduct)
                    .executeUpdate();
        }
    }

    public List<HandingProduct> getHandingProducts(PageBuilder pageBuilder) {
        String sql = "select * from handing_product " + (pageBuilder.hasWhere() ? " where " : "") + pageBuilder.generateSql();
        System.out.println("---"+sql);
        try (Connection conn = getConn()){
            return conn.createQuery(sql).executeAndFetch(HandingProduct.class);
        }
    }


}
