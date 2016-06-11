package onefengma.demo.server.services.products;

import org.sql2o.Connection;
import org.sql2o.Query;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.product.IronBuy;
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
        return getMaxCounts("iron_product");
    }

    public List<IronProduct>  getIronProducts(PageBuilder pageBuilder) {
        String sql = "select * from iron_product " + (pageBuilder.hasWhere() ? " where " : "") + pageBuilder.generateSql();
        try (Connection conn = getConn()){
            return conn.createQuery(sql).executeAndFetch(IronProduct.class);
        }
    }

    public List<IronProduct> searchIronProducts(String keyword, PageBuilder pageBuilder) {
        keyword = "%" + keyword + "%";
        String sql = "select * from iron_product " +
                "where surface like \"" + keyword + "\"" +
                "or ironType like \"" + keyword + "\"" +
                "or proPlace like \"" + keyword + "\"" +
                "or material like \"" + keyword + "\"" +
                "or title like  \"" + keyword + "\"" + pageBuilder.generateLimit();
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(IronProduct.class);
        }
    }

    public int searchIronProductsMaxCount(String keyword) {
        keyword = "%" + keyword + "%";
        String sql = "select count(*) from iron_product " +
                "where surface like \"" + keyword + "\"" +
                "or ironType like \"" + keyword + "\"" +
                "or proPlace like \"" + keyword + "\"" +
                "or material like \"" + keyword + "\"" +
                "or title like  \"" + keyword + "\"";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public void pushIronBuy(IronBuy ironBuy) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()){
            createInsertQuery(conn, "iron_buy", ironBuy).executeUpdate();
        }
    }

    public void pushIronProduct(IronProduct ironProduct) throws IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException {
        try (Connection conn = getConn()){
            createInsertQuery(conn, "iron_product", ironProduct).executeUpdate();
        }
    }

}
