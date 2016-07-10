package onefengma.demo.server.services.products;

import onefengma.demo.server.model.apibeans.product.SellerHandingBuysResponse;
import onefengma.demo.server.model.apibeans.product.SellerIronBuysResponse;
import onefengma.demo.server.model.product.*;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.order.OrderDataHelper;
import onefengma.demo.server.services.products.IronDataHelper.SellerOffer;
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

    public float getHandingPrice(String handingId) {
        String sql = "select price from handing_product where id=:id";
        try (Connection conn = getConn()) {
            Float price = conn.createQuery(sql).addParameter("id", handingId).executeScalar(Float.class);
            return price == null ? 0 : price;
        }
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

    public int getCancledCount(String userId) {
        String sql = " select count(*)"
                + " from handing_buy where userId=:userId and status=2" ;
        try(Connection connection = getConn()){
            Integer count = connection.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
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

        String supplyCountSql = "select count(*) from handing_buy_supply where handingId=:handingId";

        try (Connection conn = getConn()) {
            List<HandingBuyBrief> briefs =  conn.createQuery(sql).executeAndFetch(HandingBuyBrief.class);
            for(HandingBuyBrief brief : briefs) {
                Integer count = conn.createQuery(supplyCountSql).addParameter("handingId", brief.id).executeScalar(Integer.class);
                count = count == null ? 0 : count;
                brief.setSupplyCount(count);
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
                supplyBrief.companyName = row.getString("buyerCompanyName");
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

    public void selectHandingBuySupply(String buyerId, String handingId, String supplyUserId) {
        String sql = "update handing_buy set supplyUserId=:userId, status=1,supplyWinTime=:time where id=:handingId";

        String supplyPriceSql = "select supplyPrice from handing_buy_supply where where handingId=:handingId and sellerId=:sellerId";

        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .addParameter("time", System.currentTimeMillis())
                    .addParameter("userId", supplyUserId).executeUpdate();

            Float price = conn.createQuery(supplyPriceSql).addParameter("handingId", handingId).addParameter("sellerId", supplyUserId).executeScalar(Float.class);
            price = price == null ? 0 : price;
            float totalMoney = price;
            OrderDataHelper.instance().addIntegralByBuy(conn, buyerId, supplyUserId, totalMoney);
        }

        // 增加站内信
        InnerMessageDataHelper.instance().addInnerMessage(supplyUserId, "恭喜您成功中标", "您已经被买家加工求购中标");
    }

    public int getHandingBuyStatus(String handingId) {
        String sql = "select status from handing_buy where id=:handingId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .executeScalar(Integer.class);
        }
    }


    public SellerHandingBuysResponse getSellerHandingBuys(PageBuilder pageBuilder, String sellerId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select handing_buy.id as id, handingType, souCityId, message, userId, pushTime, timeLimit, status, supplyUserId, supplyWinTime" +
                " from handing_buy,handing_buy_seller where handing_buy_seller.handingId = handing_buy.id and sellerId=:sellerId" + pageBuilder.generateLimit();

        String supplyCountSql = "select count(*) from handing_buy_supply where handingId=:ironId";

        String maxCountSql = "select count(*) from handing_buy_seller where sellerId=:sellerId";

        String winTimesSql = "select winningTimes from seller where userId=:sellerId";
        String offerTimesSql = "select count(*) from handing_buy_supply where sellerId=:sellerId";

        SellerHandingBuysResponse response = new SellerHandingBuysResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try (Connection conn = getConn()){
            List<HandingBuyBrief> ironBuyBriefs =  conn.createQuery(sql)
                    .addParameter("sellerId", sellerId)
                    .executeAndFetch(HandingBuyBrief.class);
            for(HandingBuyBrief ironBuyBrief : ironBuyBriefs) {
                Integer count = conn.createQuery(supplyCountSql)
                        .addParameter("ironId", ironBuyBrief.id)
                        .executeScalar(Integer.class);
                count = count == null ? 0 : count;
                ironBuyBrief.setSupplyCount(count);

                // 我已中标
                if (ironBuyBrief.status == 1 && StringUtils.equals(ironBuyBrief.supplyUserId, sellerId)) {
                    ironBuyBrief.status = 4;
                }
                SellerOffer sellerOffer = HandingDataHelper.getHandingDataHelper().getSellerOffer(sellerId, ironBuyBrief.id);
                // 候选中
                if (ironBuyBrief != null && ironBuyBrief.status == 0 && sellerOffer != null) {
                    ironBuyBrief.status = 3;
                }


                ironBuyBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironBuyBrief.souCityId));
            }
            response.handings = ironBuyBriefs;

            Integer maxCount = conn.createQuery(maxCountSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            maxCount = maxCount == null ? 0 : maxCount;
            response.maxCount = maxCount;

            Integer winTimes = conn.createQuery(winTimesSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            winTimes = winTimes == null ? 0 : winTimes;

            Integer offerTimes = conn.createQuery(offerTimesSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            offerTimes = offerTimes == null ? 0 : offerTimes;

            response.offerTimes = offerTimes;
            response.offerWinRate = (float)winTimes / (float)offerTimes;

            return response;
        }
    }

    public SellerOffer getSellerOffer(String userId, String handingId) {
        String sql = "select * from handing_buy_supply where sellerId=:sellerId and handingId=:handingId";
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).addParameter("sellerId", userId).addParameter("handingId", handingId).executeAndFetchTable().rows();
            if (rows.isEmpty()) {
                return null;
            } else {
                SellerOffer sellerOffer = new SellerOffer();
                sellerOffer.price = rows.get(0).getFloat("supplyPrice");
                sellerOffer.supplyMsg = rows.get(0).getString("supplyMsg");
                return sellerOffer;
            }
        }
    }

    public boolean isHandingBuyExisted(String handingId) {
        String sql = "select count(*) from handing_buy where id=:handingId";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("handingId", handingId).executeScalar(Integer.class);
            return count != null && count != 0;
        }
    }

    public void offerHandingBuy(String sellerId, String handingId, float price, String msg) {
        String sql = "insert into handing_buy_supply set handingId=:handingId, sellerId=:sellerId, supplyPrice=:price, supplyMsg=:msg, salesmanId=0";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("handingId", handingId)
                    .addParameter("sellerId", sellerId)
                    .addParameter("price", price)
                    .addParameter("msg", msg)
                    .executeUpdate();
        }
    }

    public boolean isOffered(String sellerId, String handingId) {
        String sql = "select count(*) from handing_buy_supply where handingId=:handingId";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("handingId", handingId).executeScalar(Integer.class);
            return count != null && count != 0;
        }
    }

    public List<HandingProduct> getMyHandingProduct(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(HandingProduct.class) + " from handing_product where userId=:userId order by pushTime desc" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            List<HandingProduct> handingProducts = conn.createQuery(sql)
                    .addParameter("userId", userId).executeAndFetch(HandingProduct.class);
            for (HandingProduct handingProduct : handingProducts) {
                handingProduct.setSourceCity(CityDataHelper.instance().getCityDescById(handingProduct.souCityId));
            }
            return handingProducts;
        }
    }

    public int getMyHandingProductCount(String userId) {
        String sql = "select count(*) from handing_product where userId=:userId";
        try (Connection conn = getConn()) {
            Integer count = conn.createQuery(sql)
                    .addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public boolean isUserHandingRight(String userId, String handingId) {
        String sql = "select userId from handing_product where id=:id";
        try (Connection conn = getConn()) {
            String userQuery = conn.createQuery(sql).addParameter("id", handingId).executeScalar(String.class);
            return StringUtils.equals(userId, userQuery);
        }
    }

    public void updateHandingProduct(String handingId, float price) {
        String sql = "update handing_product set price=:price where id=:proId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("price", price)
                    .addParameter("proId", handingId).executeUpdate();
        }
    }

    public void deleteHandingProduct(String ironId) {
        String sql = "delete from handing_product where id=:proId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("proId", ironId).executeUpdate();
        }
    }


}
