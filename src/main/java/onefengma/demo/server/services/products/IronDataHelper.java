package onefengma.demo.server.services.products;

import onefengma.demo.common.DateHelper;
import onefengma.demo.server.services.order.TransactionDataHelper;
import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.others.HelpFindProduct;
import onefengma.demo.server.model.apibeans.product.SellerIronBuysResponse;
import onefengma.demo.server.model.product.IronBuy;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.model.product.IronProductBrief;
import onefengma.demo.server.model.product.IronRecommend;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.order.OrderDataHelper;

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

    public float getIronPrice(String ironId) {
        String sql = "select price from iron_product where proId=:id";
        try (Connection conn = getConn()) {
            Float price = conn.createQuery(sql).addParameter("id", ironId).executeScalar(Float.class);
            return price == null ? 0 : price;
        }
    }

    public float getIronBuySupplyPrice(String ironBuyId) {
        String sql = "select supplyPrice from iron_buy,iron_buy_supply where " +
                "iron_buy.supplyUserId = iron_buy_supply.sellerId " +
                "and iron_buy.id = iron_buy_supply.ironId " +
                "and iron_buy.id=:ironBuyId";
        try (Connection conn = getConn()) {
            Float price = conn.createQuery(sql).addParameter("ironBuyId", ironBuyId).executeScalar(Float.class);
            return price == null ? 0 : price;
        }
    }

    public int getMaxIronCounts(PageBuilder pageBuilder) {
        String sql = "select count(*)" +
                " from iron_product " +
                " left join (select productId, sum(count) as monthSellCount from product_orders) as orders " +
                " on orders.productId = iron_product.proId "  + generateWhereKey(pageBuilder, false);
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<IronProductBrief> getIronProducts(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProductBrief.class) +
                " from iron_product " +
                "left join (select productId, sum(count) as monthSellCount from product_orders where productType=0 and  finishTime<:endTime and finishTime>=:startTime) as orders " +
                " on orders.productId = iron_product.proId " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs = conn.createQuery(sql)
                    .addParameter("startTime", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                    .executeAndFetch(IronProductBrief.class);
            for (IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
        }
    }

    public int getMaxIronBuyCounts(PageBuilder pageBuilder) {
        String sql = "select count(*)" +
                " from iron_buy " + generateWhereKey(pageBuilder, false);

        LogUtils.i("------" + sql);
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<IronBuyBrief> getIronsBuy(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronBuyBrief.class) +
                " from iron_buy " + generateWhereKey(pageBuilder, true);
        String supplyCountSql = "select count(*) from iron_buy_supply where ironId=:ironId";

        try (Connection conn = getConn()) {
            List<IronBuyBrief> ironBuyBriefs = conn.createQuery(sql).executeAndFetch(IronBuyBrief.class);
            for (IronBuyBrief ironBuyBrief : ironBuyBriefs) {
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
        try (Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs = conn.createQuery(sql).executeAndFetch(IronProductBrief.class);
            for (IronProductBrief ironProductBrief : ironProductBriefs) {
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
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public void pushIronBuy(IronBuy ironBuy) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "iron_buy", ironBuy).executeUpdate();
        }
    }

    public void pushIronProduct(IronProduct ironProduct) throws IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "iron_product", ironProduct).executeUpdate();
        }
    }

    public IronDetail getIronProductById(String proId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronDetail.class) +
                " from iron_product " +
                "left join (select productId, sum(count) as monthSellCount from product_orders where productType=0 and  finishTime<:endTime and finishTime>=:startTime) as orders " +
                " on orders.productId = iron_product.proId and iron_product.proId=:proId";

        try (Connection conn = getConn()) {
            List<IronDetail> ironDetails = conn.createQuery(sql)
                    .addParameter("startTime", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("proId", proId)
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                    .executeAndFetch(IronDetail.class);
            return ironDetails.isEmpty() ? null : ironDetails.get(0);
        }
    }

    public List<IronProductBrief> getIronProductRecommend() throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProductBrief.class) + " from iron_product order by monthSellCount desc limit 0, 6";
        try (Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs = conn.createQuery(sql).executeAndFetch(IronProductBrief.class);
            for (IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
        }
    }

    public List<IronRecommend> getIronBuyRecommend() {
        String sql = "select * from iron_buy order by pushTime limit 0,10";
        try (Connection conn = getConn()) {
            List<IronRecommend> ironRecommends = new ArrayList<>();
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for (Row row : rows) {
                IronRecommend ironRecommend = new IronRecommend();
                ironRecommend.id = row.getString("id");
                ironRecommend.time = row.getLong("pushTime");
                ironRecommend.title = "求购" +  row.getString("ironType");
                ironRecommends.add(ironRecommend);
            }
            return ironRecommends;
        }
    }

    public int getCancledCount(String userId) {
        String sql = "select count(*)"
                + " from iron_buy where userId=:userId and status=2 ";
        try (Connection connection = getConn()) {
            Integer count = connection.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public void updateCancledStatis(String userId) {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public IronBuyBrief getIronBuyBrief(String ironId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronBuyBrief.class) +
                " from iron_buy where id=:ironId";

        try (Connection conn = getConn()) {
            List<IronBuyBrief> ironBuyBriefs = conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeAndFetch(IronBuyBrief.class);
            for (IronBuyBrief ironBuyBrief : ironBuyBriefs) {
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
            List<Row> rows = conn.createQuery(sql)
                    .addParameter("ironId", ironId).executeAndFetchTable().rows();
            List<SupplyBrief> supplyBriefs = new ArrayList<>();
            for (Row row : rows) {
                SupplyBrief supplyBrief = new SupplyBrief();
                supplyBrief.companyName = row.getString("companyName");
                supplyBrief.score = row.getFloat("score");
                supplyBrief.sellerId = row.getString("userId");
                supplyBrief.status = row.getInteger("status");
                supplyBrief.supplyMsg = row.getString("supplyMsg");
                supplyBrief.winningTimes = row.getInteger("winningTimes");
                supplyBrief.supplyPrice = row.getFloat("supplyPrice");
                supplyBrief.unit = row.getFloat("unit");
                supplyBriefs.add(supplyBrief);
            }
            return supplyBriefs;
        }
    }


    public String getSupplyUserId(String ironId) {
        String sql = "select supplyUserId from iron_buy where id=:ironId";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeScalar(String.class);
        }
    }

    public boolean isUserIdInSupplyList(String ironId, String userId) {
        String sql = "select sellerId from iron_buy_supply where ironId=:ironId and sellerId=:userId";
        try (Connection conn = getConn()) {
            String sellerId = conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("userId", userId)
                    .executeScalar(String.class);
            return !StringUtils.isEmpty(sellerId);
        }
    }

    public void selectIronBuySupply(String buyerId, String ironId, String supplyUserId) throws Exception {
        String sql = "update iron_buy set supplyUserId=:userId, status=1, supplyWinTime=:time where id=:ironId";

        String numberSql = "select numbers from iron_buy where where id=:ironId";
        String supplyPriceSql = "select supplyPrice from iron_buy_supply where where ironId=:ironId and sellerId=:sellerId";

        transaction ((conn)-> {
            conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("time", System.currentTimeMillis())
                    .addParameter("userId", supplyUserId).executeUpdate();

            Integer numbers = conn.createQuery(numberSql).addParameter("ironId", ironId).executeScalar(Integer.class);
            if (numbers == null || numbers == 0) {
                return;
            }
            Float price = conn.createQuery(supplyPriceSql).addParameter("ironId", ironId).addParameter("sellerId", supplyUserId).executeScalar(Float.class);
            price = price == null ? 0 : price;
            float totalMoney = price * numbers;
            // 记录交易
            TransactionDataHelper.instance().insertIronBuyTransaction(conn, supplyUserId, ironId, totalMoney, numbers);
            // 添加积分
            OrderDataHelper.instance().addIntegralByBuy(conn, buyerId, supplyUserId, totalMoney);
            // 增加站内信
            InnerMessageDataHelper.instance().addInnerMessage(supplyUserId, "恭喜您成功中标", "您已经被买家不锈钢求购中标");
        });

    }

    public int getIronBuyStatus(String ironId) {
        String sql = "select status from iron_buy where id=:ironId";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .executeScalar(Integer.class);
        }
    }

    public SellerIronBuysResponse getSellerIronBuys(PageBuilder pageBuilder, String sellerId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select iron_buy.id as id,supplyUserId,supplyWinTime, ironType, material, surface, proPlace, locationCityId, userId, message, pushTime, length, width, height, tolerance, numbers, timeLimit, status " +
                " from iron_buy,iron_buy_seller " +
                "where iron_buy_seller.ironId = iron_buy.id and sellerId=:sellerId " + pageBuilder.generateLimit();

        String supplyCountSql = "select count(*) from iron_buy_supply where ironId=:ironId";

        String maxCountSql = "select count(*) from iron_buy_seller where sellerId=:sellerId ";

        String winTimesSql = "select winningTimes from seller where userId=:sellerId";
        String offerTimesSql = "select count(*) from iron_buy_supply where sellerId=:sellerId";

        SellerIronBuysResponse response = new SellerIronBuysResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try (Connection conn = getConn()) {
            List<IronBuyBrief> ironBuyBriefs = conn.createQuery(sql)
                    .addColumnMapping("iron_buy.id", "id")
                    .addParameter("sellerId", sellerId)
                    .executeAndFetch(IronBuyBrief.class);
            for (IronBuyBrief ironBuyBrief : ironBuyBriefs) {
                Integer count = conn.createQuery(supplyCountSql)
                        .addParameter("ironId", ironBuyBrief.id)
                        .executeScalar(Integer.class);
                count = count == null ? 0 : count;
                ironBuyBrief.setSupplyCount(count);

                // 我已中标
                if (ironBuyBrief.status == 1 && StringUtils.equals(ironBuyBrief.supplyUserId, sellerId)) {
                    ironBuyBrief.status = 4;
                }

                SellerOffer sellerOffer = IronDataHelper.getIronDataHelper().getSellerOffer(sellerId, ironBuyBrief.id);
                // 候选中
                if (ironBuyBrief != null && ironBuyBrief.status == 0 && sellerOffer != null) {
                    ironBuyBrief.status = 3;
                }

                ironBuyBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironBuyBrief.locationCityId));
            }
            response.buys = ironBuyBriefs;

            Integer maxCount = conn.createQuery(maxCountSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            maxCount = maxCount == null ? 0 : maxCount;
            response.maxCount = maxCount;

            Integer winTimes = conn.createQuery(winTimesSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            winTimes = winTimes == null ? 0 : winTimes;

            Integer offerTimes = conn.createQuery(offerTimesSql).addParameter("sellerId", sellerId).executeScalar(Integer.class);
            offerTimes = offerTimes == null ? 0 : offerTimes;

            response.offerTimes = offerTimes;
            response.offerWinRate = (float) winTimes / (float) offerTimes;

            return response;
        }
    }

    public SellerOffer getSellerOffer(String userId, String buyId) {
        String sql = "select * from iron_buy_supply where sellerId=:sellerId and ironId=:ironId";
        try (Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).addParameter("sellerId", userId).addParameter("ironId", buyId).executeAndFetchTable().rows();
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

    public boolean isIronBuyExisted(String ironId) {
        String sql = "select count(*) from iron_buy where id=:ironId";
        try (Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("ironId", ironId).executeScalar(Integer.class);
            return count != null && count != 0;
        }
    }

    public void offerIronBuy(String sellerId, String ironId, float price, String msg) throws Exception {
        String sql = "insert into iron_buy_supply set " +
                "ironId=:ironId, " +
                "sellerId=:sellerId, " +
                "supplyPrice=:price, " +
                "supplyMsg=:msg, " +
                "unit=:unit, " +
                "salesmanId=0";

        transaction((conn) -> {
            conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("sellerId", sellerId)
                    .addParameter("price", price)
                    .addParameter("msg", msg)
                    .addParameter("unit", "kg")
                    .executeUpdate();

            addInBuySeller(conn, ironId, sellerId);
        });
    }

    private void addInBuySeller(Connection conn, String ironId, String userId) {
        String sql = "INSERT INTO iron_buy_seller(ironId, sellerId) " +
                "SELECT :ironId, :sellerId  FROM DUAL " +
                "WHERE NOT EXISTS" +
                "(SELECT ironId FROM iron_buy_seller WHERE ironId=:ironId and sellerId=:sellerId)";
        conn.createQuery(sql).addParameter("ironId", ironId).addParameter("sellerId", userId).executeUpdate();
    }

    public boolean isOffered(String sellerId, String ironId) {
        String sql = "select count(*) from iron_buy_supply where ironId=:ironId";
        try (Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("ironId", ironId).executeScalar(Integer.class);
            return count != null && count != 0;
        }
    }

    public List<IronProduct> getMyIronProduct(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProduct.class) + " from iron_product where userId=:userId order by pushTime desc" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            List<IronProduct> ironProducts = conn.createQuery(sql)
                    .addParameter("userId", userId).executeAndFetch(IronProduct.class);
            for (IronProduct ironProduct : ironProducts) {
                ironProduct.setSourceCity(CityDataHelper.instance().getCityDescById(ironProduct.sourceCityId));
            }
            return ironProducts;
        }
    }

    public int getMyIronProductCount(String userId) {
        String sql = "select count(*) from iron_product where userId=:userId";
        try (Connection conn = getConn()) {
            Integer count = conn.createQuery(sql)
                    .addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public boolean isUserIronRight(String userId, String ironId) {
        String sql = "select userId from iron_product where proId=:id";
        try (Connection conn = getConn()) {
            String userQuery = conn.createQuery(sql).addParameter("id", ironId).executeScalar(String.class);
            return StringUtils.equals(userId, userQuery);
        }
    }

    public void updateIronProduct(String ironId, long numbers, float price, String spec) {
        String sql = "update iron_product set numbers=:numbers, price=:price, title=:title where proId=:proId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("numbers", numbers)
                    .addParameter("price", price)
                    .addParameter("title", spec)
                    .addParameter("proId", ironId).executeUpdate();
        }
    }

    public void deleteIronProduct(String ironId) {
        String sql = "delete from iron_product where proId=:proId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("proId", ironId).executeUpdate();
        }
    }

    public IronBuyOfferDetail getWinSellerOffer(String ironBuyId, String userId) {
        String sql = "select " + generateFiledString(IronBuyOfferDetail.class) + " from iron_buy_supply where ironId=:id and sellerId=:userId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", ironBuyId).addParameter("userId", userId).executeAndFetchFirst(IronBuyOfferDetail.class);
        }
    }

    public void insertFindHelpProduct(HelpFindProduct helpFindProduct) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try(Connection conn = getConn()) {
            createInsertQuery(conn, "help_find_product", helpFindProduct);
        }
    }

    public static class IronBuyOfferDetail {
        public String id;
        public String ironId;
        public String sellerId;
        public float supplyPrice;
        public String supplyMsg;
        public int status;
        public int salesmanId;
    }

    public static class SellerOffer {
        public float price;
        public String supplyMsg;
    }


}
