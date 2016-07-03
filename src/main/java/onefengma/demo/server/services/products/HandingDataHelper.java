package onefengma.demo.server.services.products;

import onefengma.demo.server.model.product.*;
import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.services.funcs.CityDataHelper;

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

    public int getMaxCount(PageBuilder pageBuilder) {
        String sql = "select count(*)"
            + " from handing_product " + generateWhereKey(pageBuilder, false);
        try(Connection connection = getConn()){
            return connection.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public void insertHandingProduct(HandingProduct handingProduct) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "handing_product", handingProduct)
                    .executeUpdate();
        }
    }

    public List<HandingProductBrief> getHandingProducts(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(HandingProductBrief.class)
                + " from handing_product " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()) {
            List<HandingProductBrief> briefs =  conn.createQuery(sql).executeAndFetch(HandingProductBrief.class);
            for(HandingProductBrief brief : briefs) {
                brief.setSourceCity(CityDataHelper.instance().getCityDescById(brief.souCityId));
            }
            return briefs;
        }
    }

    public int getMaxBuyCount(PageBuilder pageBuilder) {
        String sql = "select count(*)"
                + " from handing_buy " + generateWhereKey(pageBuilder, false);
        try(Connection connection = getConn()){
            return connection.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<HandingBuyBrief> getHandingBuys(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(HandingBuyBrief.class)
                + " from handing_buy " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()) {
            List<HandingBuyBrief> briefs =  conn.createQuery(sql).executeAndFetch(HandingBuyBrief.class);
            for(HandingBuyBrief brief : briefs) {
                brief.setSourceCity(CityDataHelper.instance().getCityDescById(brief.souCityId));
            }
            return briefs;
        }
    }

    private String generateWhereKey(PageBuilder pageBuilder, boolean withLimit) {
        StringBuilder whereBuilder = new StringBuilder();
        if (StringUtils.isEmpty(pageBuilder.keyword)) {
            whereBuilder.append(pageBuilder.hasWhere() ? " where " : "");
        } else {
            whereBuilder.append(generateKeyword(pageBuilder.keyword));
            whereBuilder.append(pageBuilder.hasWhere() ? " and " : "");
        }
        whereBuilder.append(pageBuilder.generateSql(withLimit));
        return whereBuilder.toString();
    }

    public String generateKeyword(String keyword) {
        return "where title like \"%" + keyword + "%\"" +
                "or type like \"%" + keyword + "%\"";
    }

    public void pushHandingBuy(HandingBuy handingBuy) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "handing_buy", handingBuy).executeUpdate();
        }
    }

    public List<HandingProductBrief> searchHandingProduct(String keyword, PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        keyword = "%" + keyword + "%";
        String sql = "select " + generateFiledString(HandingProductBrief.class) + " from handing_product " +
                "where title like \"" + keyword + "\"" +
                "or type like \"" + keyword + "\"" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            List<HandingProductBrief> briefs =  conn.createQuery(sql).executeAndFetch(HandingProductBrief.class);
            for(HandingProductBrief brief : briefs) {
                brief.setSourceCity(CityDataHelper.instance().getCityDescById(brief.souCityId));
            }
            return briefs;
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

    public HandingDetail getHandingProductById(String id) {
        String sql = "select * from handing_product where id=:id";
        try(Connection conn = getConn()) {
            List<HandingDetail> handingProducts = conn.createQuery(sql).addParameter("id", id).executeAndFetch(HandingDetail.class);
            if (handingProducts.isEmpty()) {
                return null;
            } else {
                return handingProducts.get(0);
            }
        }
    }

    public List<HandingProductBrief> getHandingProductRecommend() throws NoSuchFieldException, IllegalAccessException {
        String sql =  "select " + generateFiledString(HandingProductBrief.class) + " from handing_product order by monthSellCount desc limit 0, 6";
        try(Connection conn = getConn()) {
            List<HandingProductBrief> briefs =  conn.createQuery(sql).executeAndFetch(HandingProductBrief.class);
            for(HandingProductBrief brief : briefs) {
                brief.setSourceCity(CityDataHelper.instance().getCityDescById(brief.souCityId));
            }
            return briefs;
        }
    }

}
