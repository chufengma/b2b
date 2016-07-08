package onefengma.demo.server.services.products;

import onefengma.demo.server.model.product.*;
import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.services.funcs.CityDataHelper;
import org.sql2o.data.Row;

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

    public int getCancledCount(PageBuilder pageBuilder, String userId) {
        pageBuilder.addEqualWhere("status", 2);
        String sql = "select count(*)"
                + " from handing_buy " + generateWhereKey(pageBuilder, false);
        try(Connection connection = getConn()){
            return connection.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public void updateCancledStatis(String userId) {
        String sql = "update handing_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("userId", userId).executeUpdate();
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

    public HandingBuyBrief getHandingBrief(String handingId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(HandingBuyBrief.class)
                + " from handing_buy where id=:handingId";
        try (Connection conn = getConn()) {
            List<HandingBuyBrief> briefs =  conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .executeAndFetch(HandingBuyBrief.class);
            for(HandingBuyBrief brief : briefs) {
                brief.setSourceCity(CityDataHelper.instance().getCityDescById(brief.souCityId));
            }
            if (briefs.isEmpty()) {
                return null;
            }
            return briefs.get(0);
        }
    }

    public List<SupplyBrief> getHandingBuySupplies(String handignId) {
        String sql = "select * from handing_buy_supply,seller where handingId=:handingId and sellerId=userId";
        try (Connection conn = getConn()) {
            List<Row> rows =  conn.createQuery(sql)
                    .addParameter("handingId", handignId).executeAndFetchTable().rows();
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

    public String getSupplyUserId(String handingId) {
        String sql = "select supplyUserId from handing_buy where id=:handingId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .executeScalar(String.class);
        }
    }

    public boolean isUserIdInSupplyList(String handingId, String userId) {
        String sql = "select sellerId from handing_buy_supply where handingId=:handingId and sellerId=:userId";
        try(Connection conn = getConn()) {
            String sellerId =  conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .addParameter("userId", userId)
                    .executeScalar(String.class);
            return !StringUtils.isEmpty(sellerId);
        }
    }

    public void selectHandingBuySupply(String handingId, String supplyUserId) {
        String sql = "update handing_buy set supplyUserId=:userId, status=1 where id=:handingId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .addParameter("userId", supplyUserId).executeUpdate();
        }
    }

    public int getHandingBuyStatus(String handingId) {
        String sql = "select status from handing_buy where id=:handingId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .executeScalar(Integer.class);
        }
    }

}
