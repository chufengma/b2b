package onefengma.demo.server.services.products;

import org.sql2o.Connection;
import org.sql2o.Query;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.product.IronProduct;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class IronDataHelper extends BaseDataHelper {

    private static IronDataHelper ironDataHelper;

    public static IronDataHelper getIronDataHelper() {
        if (ironDataHelper == null) {
            ironDataHelper = new IronDataHelper();
        }
        return ironDataHelper;
    }

    public int getMaxCounts() {
        String sql = "select count(id) from iron_product";
        try(Connection conn = getConn()) {
            int count = conn.createQuery(sql).executeScalar(Integer.class);
            return count;
        }
    }

    public List<IronProduct>  getIronProducts(PageBuilder pageBuilder) {
        if (pageBuilder.pageCount <=0 || pageBuilder.pageCount >= 300) {
            pageBuilder.pageCount = 300;
        }
        String sql = "select * from iron_product " + (pageBuilder.hasWhere() ? " where " : "") + pageBuilder.generateSql();
        System.out.print("---" + sql);
        try (Connection conn = getConn()){
            return conn.createQuery(sql).executeAndFetch(IronProduct.class);
        }
    }

    public void pushIronProduct(IronProduct ironProduct) throws IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException {
        try (Connection conn = getConn()){
            createInsertQuery(conn, "iron_product", ironProduct).executeUpdate();
        }
    }

}
