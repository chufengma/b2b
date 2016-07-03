package onefengma.demo.server.services.products;

import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.model.product.*;
import org.apache.commons.logging.Log;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.services.funcs.CityDataHelper;

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

    public int getMaxIronCounts(PageBuilder pageBuilder) {
        String sql = "select count(*)" +
                " from iron_product " + generateWhereKey(pageBuilder, false);
        try (Connection conn = getConn()){
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<IronProductBrief>  getIronProducts(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProductBrief.class) +
                " from iron_product " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()){
            List<IronProductBrief> ironProductBriefs =  conn.createQuery(sql).executeAndFetch(IronProductBrief.class);
            for(IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
        }
    }

    public int getMaxIronBuyCounts(PageBuilder pageBuilder) {
        String sql = "select count(*)" +
                " from iron_buy " + generateWhereKey(pageBuilder, false);

        LogUtils.i("------" + sql);
        try (Connection conn = getConn()){
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<IronBuyBrief> getIronsBuy(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronBuyBrief.class) +
                " from iron_buy " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()){
            List<IronBuyBrief> ironBuyBriefs =  conn.createQuery(sql).executeAndFetch(IronBuyBrief.class);
            for(IronBuyBrief ironBuyBrief : ironBuyBriefs) {
                ironBuyBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironBuyBrief.locationCityId));
            }
            return ironBuyBriefs;
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

    private String generateKeyword(String keyword) {
        return StringUtils.isEmpty(keyword) ? "" : ("where surface like \"%" + keyword + "%\" " +
                "or ironType like \"%" + keyword + "%\" " +
                "or proPlace like \"%" + keyword + "%\" " +
                "or material like \"%" + keyword + "%\" " +
                "or title like  \"%" + keyword + "%\" ");
    }

    public List<IronProductBrief> searchIronProducts(String keyword, PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        keyword = "%" + keyword + "%";
        String sql = "select " + generateFiledString(IronProductBrief.class) + " from iron_product " +
                "where surface like \"" + keyword + "\"" +
                "or ironType like \"" + keyword + "\"" +
                "or proPlace like \"" + keyword + "\"" +
                "or material like \"" + keyword + "\"" +
                "or title like  \"" + keyword + "\"" + pageBuilder.generateSql(true);
        try(Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs =  conn.createQuery(sql).executeAndFetch(IronProductBrief.class);
            for(IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
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

    public IronDetail getIronProductById(String proId) {
        String sql = "select * from iron_product where proId=:proId";
        try(Connection conn = getConn()) {
           List<IronDetail> ironProducts = conn.createQuery(sql).addParameter("proId", proId).executeAndFetch(IronDetail.class);
           if (ironProducts.isEmpty()) {
               return null;
           } else {
               return ironProducts.get(0);
           }
        }
    }

    public List<IronProductBrief> getIronProductRecommend() throws NoSuchFieldException, IllegalAccessException {
        String sql =  "select " + generateFiledString(IronProductBrief.class) + " from iron_product order by monthSellCount desc limit 0, 6";
        try(Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs =  conn.createQuery(sql).executeAndFetch(IronProductBrief.class);
            for(IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
        }
    }

    public List<IronRecommend> getIronBuyRecommend() {
        String sql = "select * from iron_buy order by pushTime limit 0,10";
        try(Connection conn = getConn()) {
            List<IronRecommend> ironRecommends = new ArrayList<>();
            List<IronBuy> ironBuys = conn.createQuery(sql).executeAndFetch(IronBuy.class);
            for(IronBuy ironBuy : ironBuys) {
                IronRecommend ironRecommend = new IronRecommend();
                ironRecommend.id = ironBuy.id;
                ironRecommend.time = ironBuy.pushTime;
                ironRecommend.title = "求购" + ironBuy.ironType;
                ironRecommends.add(ironRecommend);
            }
            return ironRecommends;
        }
    }

}
