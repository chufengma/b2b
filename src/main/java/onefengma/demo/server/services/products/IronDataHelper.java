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
import org.sql2o.data.Row;

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
        String supplyCountSql = "select count(*) from iron_buy_supply where ironId=:ironId";

        try (Connection conn = getConn()){
            List<IronBuyBrief> ironBuyBriefs =  conn.createQuery(sql).executeAndFetch(IronBuyBrief.class);
            for(IronBuyBrief ironBuyBrief : ironBuyBriefs) {
                Integer count = conn.createQuery(supplyCountSql).addParameter("ironId", ironBuyBrief.id).executeScalar(Integer.class);
                count = count == null ? 0 : count;
                ironBuyBrief.setSupplyCount(count);
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

    public int getCancledCount(String userId) {
        String sql = "select count(*)"
                + " from iron_buy where userId=:userId and status=2 ";
        try(Connection connection = getConn()){
            Integer count = connection.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public void updateCancledStatis(String userId) {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public IronBuyBrief getIronBuyBrief(String ironId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronBuyBrief.class) +
                " from iron_buy where id=:ironId";

        try (Connection conn = getConn()){
            List<IronBuyBrief> ironBuyBriefs =  conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeAndFetch(IronBuyBrief.class);
            for(IronBuyBrief ironBuyBrief : ironBuyBriefs) {
                ironBuyBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironBuyBrief.locationCityId));
            }
            if (ironBuyBriefs.isEmpty()) {
                return null;
            }
            return ironBuyBriefs.get(0);
        }
    }

    public List<SupplyBrief> getIronBuySupplies(String ironId) {
        String sql = "select * from iron_buy_supply,seller where ironId=:ironId and sellerId=userId";
        try (Connection conn = getConn()) {
            List<Row> rows =  conn.createQuery(sql)
                    .addParameter("ironId", ironId).executeAndFetchTable().rows();
            List<SupplyBrief> supplyBriefs = new ArrayList<>();
            for(Row row : rows) {
                SupplyBrief supplyBrief = new SupplyBrief();
                supplyBrief.companyName = row.getString("companyName");
                supplyBrief.score = row.getFloat("score");
                supplyBrief.sellerId = row.getString("userId");
                supplyBrief.status = row.getInteger("status");
                supplyBrief.supplyMsg = row.getString("supplyMsg");
                supplyBrief.winningTimes = row.getInteger("winningTimes");
                supplyBrief.supplyPrice = row.getFloat("supplyPrice");
                supplyBriefs.add(supplyBrief);
            }
            return supplyBriefs;
        }
    }


    public String getSupplyUserId(String ironId) {
        String sql = "select supplyUserId from iron_buy where id=:ironId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeScalar(String.class);
        }
    }

    public boolean isUserIdInSupplyList(String ironId, String userId) {
        String sql = "select sellerId from iron_buy_supply where ironId=:ironId and sellerId=:userId";
        try(Connection conn = getConn()) {
            String sellerId =  conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("userId", userId)
                    .executeScalar(String.class);
            return !StringUtils.isEmpty(sellerId);
        }
    }

    public void selectIronBuySupply(String ironId, String supplyUserId) {
        String sql = "update iron_buy set supplyUserId=:userId, status=1 where id=:ironId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("userId", supplyUserId).executeUpdate();
        }
    }

    public int getIronBuyStatus(String ironId) {
        String sql = "select status from iron_buy where id=:ironId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeScalar(Integer.class);
        }
    }


}
