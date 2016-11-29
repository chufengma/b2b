package onefengma.demo.server.services.products;

import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.NumberUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ThreadUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.PushManager;
import onefengma.demo.server.model.MyAllHistoryInfo;
import onefengma.demo.server.model.MyBuyHistoryInfo;
import onefengma.demo.server.model.MyOfferHistoryInfo;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.apibeans.others.HelpFindProduct;
import onefengma.demo.server.model.apibeans.product.SellerIronBuysResponse;
import onefengma.demo.server.model.apibeans.qt.QtListResponse;
import onefengma.demo.server.model.mobile.BasePushData;
import onefengma.demo.server.model.mobile.BuyPushData;
import onefengma.demo.server.model.mobile.LoseOfferPushData;
import onefengma.demo.server.model.mobile.NewIronBuyPushData;
import onefengma.demo.server.model.mobile.WinOfferPushData;
import onefengma.demo.server.model.product.IronBuy;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.model.product.IronProductBrief;
import onefengma.demo.server.model.product.IronRecommend;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.model.qt.QtBrief;
import onefengma.demo.server.model.qt.QtDetail;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.order.OrderDataHelper;
import onefengma.demo.server.services.order.TransactionDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;
import onefengma.demo.server.services.user.UserMessageDataHelper;

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
                " on orders.productId = iron_product.proId " + generateWhereKey(pageBuilder, false);
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<IronProductBrief> getIronProducts(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProductBrief.class) +
                " from iron_product " +
                "left join (select productId, convert(sum(totalMoney), decimal) as monthSellCount from product_orders where productType=0 and (status=1 or status=2) and finishTime<:endTime and finishTime>=:startTime group by productId) as orders " +
                " on orders.productId = iron_product.proId " + generateWhereKey(pageBuilder, true);

        try (Connection conn = getConn()) {
            List<IronProductBrief> ironProductBriefs = conn.createQuery(sql)
                    .addParameter("startTime", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                    .executeAndFetch(IronProductBrief.class);
            for (IronProductBrief ironProductBrief : ironProductBriefs) {
                ironProductBrief.monthSellCount = ironProductBrief.monthSellCount == null ? new BigDecimal(0) : ironProductBrief.monthSellCount;
                ironProductBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironProductBrief.sourceCityId));
            }
            return ironProductBriefs;
        }
    }

    public int getMaxIronBuyCounts(PageBuilder pageBuilder) {
        String sql = "select count(*)" +
                " from iron_buy " + generateWhereKey(pageBuilder, false);

        try (Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public int getMaxIronBuyNewSupplyNum(PageBuilder pageBuilder) {
        String sql = "select sum(newSupplyNum)" +
                " from iron_buy " + generateWhereKey(pageBuilder, false);

        try (Connection conn = getConn()) {
            Integer num = conn.createQuery(sql).executeScalar(Integer.class);
            return num == null ? 0 : num;
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
        return StringUtils.isEmpty(keyword) ? "" : ("where (surface like \"%" + keyword + "%\" " +
                "or ironType like \"%" + keyword + "%\" " +
                "or proPlace like \"%" + keyword + "%\" " +
                "or material like \"%" + keyword + "%\" " +
                "or title like  \"%" + keyword + "%\") ");
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
            pushToSellers(ironBuy);
        }
    }

    public void editIronBuy(IronBuy ironBuy) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        String sql = "update iron_buy set ironType=:ironType," +
                "    material=:material," +
                "    surface=:surface," +
                "    proPlace=:proPlace," +
                "    locationCityId=:locationCityId," +
                "    message=:message," +
                "    length=:length," +
                "    width=:width," +
                "    height=:height," +
                "    tolerance=:tolerance," +
                "    numbers=:numbers," +
                "    timeLimit=:timeLimit," +
                "    salesmanId=:salesmanId," +
                "    unit=:unit,editStatus=1 where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("ironType", ironBuy.ironType)
                    .addParameter("material", ironBuy.material)
                    .addParameter("surface", ironBuy.surface)
                    .addParameter("proPlace", ironBuy.proPlace)
                    .addParameter("locationCityId", ironBuy.locationCityId)
                    .addParameter("message", ironBuy.message)
                    .addParameter("length", ironBuy.length)
                    .addParameter("width", ironBuy.width)
                    .addParameter("height", ironBuy.height)
                    .addParameter("tolerance", ironBuy.tolerance)
                    .addParameter("numbers", ironBuy.numbers)
                    .addParameter("timeLimit", ironBuy.timeLimit)
                    .addParameter("salesmanId", ironBuy.salesmanId)
                    .addParameter("unit", ironBuy.unit)
                    .addParameter("id", ironBuy.id)
                    .executeUpdate();
            pushToSellers(ironBuy);
        }
    }

    private void pushToSellers(IronBuy ironBuy) {
        ThreadUtils.instance().post(new Runnable() {
            @Override
            public void run() {
                String userSql = "select userId from iron_product where (surface like '%" + ironBuy.surface + "%'" +
                        "and ironType like '%" + ironBuy.ironType + "%'" +
                        "and proPlace like '%" + ironBuy.proPlace + "%'" +
                        "and material like '%" + ironBuy.material + "%') and userId<> :userId group by userId";

                String subSql = "select userId from seller_subscribe where (surfaces like '%" + ironBuy.surface + "%'" +
                        "and types like '%" + ironBuy.ironType + "%'" +
                        "and proPlaces like '%" + ironBuy.proPlace + "%'" +
                        "and materials like '%" + ironBuy.material + "%') and userId<> :userId group by userId";

                try (Connection conn = getConn()) {
                    List<String> users = conn.createQuery(userSql)
                            .addParameter("userId", ironBuy.userId)
                            .executeAndFetch(String.class);

                    List<String> subUsers = conn.createQuery(subSql)
                            .addParameter("userId", ironBuy.userId)
                            .executeAndFetch(String.class);

                    List<String> tmpList = new ArrayList<String>(users);
                    for(String newUser : subUsers) {
                        if (!users.contains(newUser)) {
                            tmpList.add(newUser);
                        }
                    }

                    for (String userId : tmpList) {
                        addInBuySeller(conn, ironBuy.id, userId);
                        String message = "有人求购" + generateIroBuyMessage(ironBuy) + "，请前往淘求购或后台报价管理页面刷新查看";
                        UserMessageDataHelper.instance().setUserMessage(userId, message);

                        NewIronBuyPushData newIronBuyPushData = new NewIronBuyPushData(userId);
                        newIronBuyPushData.title = "有您感兴趣的求购";
                        newIronBuyPushData.desc = "有人求购" + generateIroBuyMessage(ironBuy);
                        PushManager.instance().pushData(newIronBuyPushData);
                    }
                }
            }
        });
    }

    public String generateIroBuyMessage(IronBuy ironBuy) {
        return ironBuy.ironType + " " + ironBuy.surface + " " + ironBuy.material + " "
                + ironBuy.height + "*" + ironBuy.width + "*" + ironBuy.length  + " "
                + ironBuy.tolerance + " " + ironBuy.numbers + " " + ironBuy.unit;
    }

    public String generateIronBuyMessage(IronBuyBrief ironBuy) {
        return ironBuy.ironType + " " + ironBuy.surface + " " + ironBuy.material + " "
                + ironBuy.height + "*" + ironBuy.width + "*" + ironBuy.length  + " "
                + ironBuy.tolerance + " " + ironBuy.numbers + " " + ironBuy.unit;
    }


    public void pushIronProduct(IronProduct ironProduct) throws IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "iron_product", ironProduct).executeUpdate();
        }
    }

    public IronDetail getIronProductById(String proId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronDetail.class) +
                " from iron_product " +
                "left join (select productId, sum(totalMoney) as monthSellCount from product_orders where productType=0 and  finishTime<:endTime and finishTime>=:startTime group by productId) as orders " +
                " on orders.productId = iron_product.proId where iron_product.proId=:proId";

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

    public List<IronRecommend> getIronBuyRecommend() throws NoSuchFieldException, IllegalAccessException {
        String sql = "select * from iron_buy order by pushTime desc limit 0,10";
        try (Connection conn = getConn()) {
            List<IronRecommend> ironRecommends = new ArrayList<>();
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for (Row row : rows) {
                IronRecommend ironRecommend = new IronRecommend();
                ironRecommend.id = row.getString("id");
                ironRecommend.time = row.getLong("pushTime");
                ironRecommend.title = row.getString("ironType") + " " + row.getString("material")
                        + " " + row.getString("surface") + " " + CityDataHelper.instance().getCityDescById(row.getString("locationCityId"));
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

    public void updateBuyStatusByUserId(String userId) {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public void updateBuyStatusByIronId(String ironBuyId) {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("id", ironBuyId).executeUpdate();
        }
    }

    public void updateBuyStatusBySellerId(String sellerId) {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 " +
                "and id in (select ironId from iron_buy_seller where sellerId=:sellerId)";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis())
                    .addParameter("sellerId", sellerId).executeUpdate();
        }
    }

    public int getMySellerIronBuy(String sellerId, String ironId) {
        IronDataHelper.getIronDataHelper().updateBuyStatusBySellerId(sellerId);
        String sql = "select count(*) from iron_buy,iron_buy_seller " +
                "where iron_buy.id = iron_buy_seller.ironId and iron_buy.id = :ironId and sellerId=:sellerId";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("ironId", ironId)
                    .addParameter("sellerId", sellerId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public void updateBuyStatus() {
        String sql = "update iron_buy " +
                "set status = 2 where (pushTime+timeLimit) < :currentTime and status = 0 ";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("currentTime", System.currentTimeMillis()).executeUpdate();
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

    public void resetIronBuyNewOffersCount(String ironId) {
        String sql = "update iron_buy set lastGetDetailTime=:lastGetDetailTime, newSupplyNum=0 where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("lastGetDetailTime", System.currentTimeMillis())
                    .addParameter("id", ironId).executeUpdate();
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
                supplyBrief.winningTimes = SellerDataHelper.instance().getUserSupplyWinnerTimes(supplyBrief.sellerId);
                supplyBrief.supplyPrice = row.getFloat("supplyPrice");
                supplyBrief.unit = row.getString("unit");
                supplyBrief.offerTime = row.getLong("offerTime");
                supplyBrief.contact = row.getString("contact");
                supplyBriefs.add(supplyBrief);
            }
            return supplyBriefs;
        }
    }

    public List<SupplyBrief> getIronBuySuppliesMissed(String ironId) {
        String sql = "select * from iron_buy_seller_miss,seller where ironId=:ironId and sellerId=userId";
        try (Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql)
                    .addParameter("ironId", ironId).executeAndFetchTable().rows();
            List<SupplyBrief> supplyBriefs = new ArrayList<>();
            for (Row row : rows) {
                SupplyBrief supplyBrief = new SupplyBrief();
                supplyBrief.companyName = row.getString("companyName");
                supplyBrief.score = row.getFloat("score");
                supplyBrief.sellerId = row.getString("userId");
                supplyBrief.supplyMsg = "-1";
                supplyBrief.supplyPrice = -1;
                supplyBrief.winningTimes = SellerDataHelper.instance().getUserSupplyWinnerTimes(supplyBrief.sellerId);
                supplyBrief.contact = row.getString("contact");
                supplyBriefs.add(supplyBrief);
            }
            return supplyBriefs;
        }
    }

    private SupplyBrief generageSupplyBrief(Row row) {
        SupplyBrief supplyBrief = new SupplyBrief();
        supplyBrief.companyName = row.getString("companyName");
        supplyBrief.score = row.getFloat("score");
        supplyBrief.sellerId = row.getString("userId");
        supplyBrief.status = row.getInteger("status");
        supplyBrief.supplyMsg = row.getString("supplyMsg");
        supplyBrief.winningTimes = SellerDataHelper.instance().getUserSupplyWinnerTimes(supplyBrief.sellerId);
        supplyBrief.supplyPrice = row.getFloat("supplyPrice");
        supplyBrief.unit = row.getString("unit");
        supplyBrief.offerTime = row.getLong("offerTime");
        supplyBrief.contact = row.getString("contact");
        return supplyBrief;
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

        String numberSql = "select numbers from iron_buy where id=:ironId";
        String supplyPriceSql = "select supplyPrice from iron_buy_supply where ironId=:ironId and sellerId=:sellerId";

        transaction((conn) -> {
            conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("time", System.currentTimeMillis())
                    .addParameter("userId", supplyUserId).executeUpdate();

            Float numbers = conn.createQuery(numberSql).addParameter("ironId", ironId).executeScalar(Float.class);
            if (numbers == null || numbers == 0) {
                return;
            }
            Float price = conn.createQuery(supplyPriceSql).addParameter("ironId", ironId).addParameter("sellerId", supplyUserId).executeScalar(Float.class);
            price = price == null ? 0 : price;
            float totalMoney = price * numbers;
            // 记录交易
            TransactionDataHelper.instance().insertIronBuyTransaction(conn, buyerId, supplyUserId, ironId, totalMoney, numbers);
            // 添加积分
            OrderDataHelper.instance().addIntegralByBuy(conn, buyerId, supplyUserId, totalMoney);
            // 增加推送消息
            IronBuyBrief ironBuyBrief = getIronBuyBrief(ironId);
            if (ironBuyBrief != null) {
                String message = "恭喜您！您报价的 " + generateIronBuyMessage(ironBuyBrief) + " 已中标，请联系对方吧 : " + UserDataHelper.instance().getUserMobile(buyerId);
                UserMessageDataHelper.instance().setUserMessage(supplyUserId, message);
                // 增加站内信
                InnerMessageDataHelper.instance().addInnerMessage(supplyUserId, "恭喜您成功中标", message);

                // 推送至mobile
                System.out.println("-------userId-" + supplyUserId);
                WinOfferPushData pushData = new WinOfferPushData(supplyUserId);
                pushData.title = "恭喜您成功中标！";
                pushData.desc = message;
                pushData.setIronBuyBrief(ironBuyBrief);
                PushManager.instance().pushData(pushData);

                // 推送至竞争失败者
                List<SupplyBrief> losers = IronDataHelper.getIronDataHelper().getIronBuySupplies(ironId);
                if (losers != null) {
                    String loseMessage = "很遗憾！您报价的 " + generateIronBuyMessage(ironBuyBrief) + " 未中标.";
                    for(SupplyBrief supplyBrief : losers) {
                        if (StringUtils.equals(supplyBrief.sellerId, supplyUserId)) {
                            continue;
                        }
                        LoseOfferPushData losepushData = new LoseOfferPushData(supplyUserId);
                        pushData.title = "很遗憾您竞标失败";
                        pushData.desc = loseMessage;
                        pushData.setIronBuyBrief(ironBuyBrief);
                        PushManager.instance().pushData(losepushData);
                    }
                }
            }
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

    public SellerIronBuysResponse getSellerIronBuys(PageBuilder pageBuilder, String sellerId, int status) throws NoSuchFieldException, IllegalAccessException {
        String statusStr = status == -1 ? " and iron_buy.status<>2 " : " and iron_buy.status=" + status + " ";
        if (status == 6) {
            statusStr = " and iron_buy.status <> 0 and iron_buy.supplyUserId<>sellerId ";
        } else if (status == 3) {
            statusStr = " and iron_buy.status = 0 ";
        } else if (status == 4) {
            statusStr = " and iron_buy.status = 1 and iron_buy.supplyUserId=sellerId ";
        } else if (status == 0 ) {
            statusStr = " and iron_buy.status = 0 and iron_buy.id not in (select ironId from iron_buy_supply where sellerId=:sellerId) ";
        }
        String fileds = " iron_buy.id as id,supplyUserId,supplyWinTime, ironType, material, surface, proPlace, locationCityId, userId, message, pushTime, length, width, height, tolerance, numbers, timeLimit, iron_buy.unit, iron_buy.status as status ";

        String sql = "";
        if (status == 6 || status == 3 || status == 4) {
            sql = "select " + fileds + " from iron_buy,iron_buy_supply " +
                    "where iron_buy_supply.ironId = iron_buy.id and sellerId=:sellerId " + statusStr + "  order by pushTime desc  " + pageBuilder.generateLimit();
        } else {
            sql = "select " + fileds + " from iron_buy,iron_buy_seller " +
                    "where iron_buy_seller.ironId = iron_buy.id and sellerId=:sellerId " + statusStr + "  order by pushTime desc  " + pageBuilder.generateLimit();
        }

        String supplyCountSql = "select count(*) from iron_buy_supply where ironId=:ironId";

        String maxCountSql = "";
        if (status == 6 || status == 3 || status == 4) {
            maxCountSql = "select count(*)" +
                    " from iron_buy,iron_buy_supply where iron_buy_supply.ironId = iron_buy.id and sellerId=:sellerId " + statusStr;
        } else {
            maxCountSql = "select count(*)" +
                    " from iron_buy,iron_buy_seller where iron_buy_seller.ironId = iron_buy.id and sellerId=:sellerId " + statusStr;
        }

        String winTimesSql = "select count(supplyUserId) as winningTimes from iron_buy where supplyUserId=:sellerId";
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

                if (status == 6) {
                    ironBuyBrief.status = 6;
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
                sellerOffer.unit = rows.get(0).getString("unit");
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

    public void missIronBuyOffer(String ironId, String sellerId) throws NoSuchFieldException, IllegalAccessException {
        String copySql = "insert into iron_buy_seller_miss set sellerId=:sellerId , ironId=:ironId,  missTime=:missTime";
        String sql = "delete from iron_buy_seller where sellerId=:sellerId and ironId=:ironId";

        String updateIronBuySql = "update iron_buy set newSupplyNum=(newSupplyNum+1) where id=:id";

        try(Connection conn = getConn()) {
            conn.createQuery(copySql).addParameter("ironId", ironId).addParameter("sellerId", sellerId).addParameter("missTime", System.currentTimeMillis()).executeUpdate();
            conn.createQuery(sql).addParameter("ironId", ironId).addParameter("sellerId", sellerId).executeUpdate();

            // add new offer count
            conn.createQuery(updateIronBuySql).addParameter("id", ironId).executeUpdate();

            // 推送至mobile
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(ironId);
            String message = generateIronBuyMessage(ironBuyBrief);
            Seller seller = SellerDataHelper.instance().getSeller(sellerId);
            if (seller != null) {
                message = seller.companyName + "公司 已放弃对您的" + message + "求购报价.";
            }
            BuyPushData pushData = new BuyPushData(ironBuyBrief.userId, BasePushData.PUSH_TYPE_OFFER_MISS);
            pushData.title = "有商家已放弃您的求购";
            pushData.desc = message;
            pushData.ironBuyBrief = ironBuyBrief;
            PageBuilder pageBuilder = new PageBuilder(0, 10)
                    .addEqualWhere("userId", ironBuyBrief.userId)
                    .addEqualWhere("status", 0);
            pushData.newSupplyNums = IronDataHelper.getIronDataHelper().getMaxIronBuyNewSupplyNum(pageBuilder);

            PushManager.instance().pushData(pushData);
        }
    }

    public void offerIronBuy(String sellerId, String ironId, float price, String msg, String unit) throws Exception {
        String sql = "insert into iron_buy_supply set " +
                "ironId=:ironId, " +
                "sellerId=:sellerId, " +
                "supplyPrice=:price, " +
                "supplyMsg=:msg, " +
                "unit=:unit, " +
                "salesmanId=0," +
                "offerTime=:time";

        String updateIronBuySql = "update iron_buy set newSupplyNum=(newSupplyNum+1),editStatus=1 where id=:id";

        transaction((conn) -> {
            conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("sellerId", sellerId)
                    .addParameter("price", price)
                    .addParameter("msg", msg)
                    .addParameter("time", System.currentTimeMillis())
                    .addParameter("unit", unit)
                    .executeUpdate();

            addInBuySeller(conn, ironId, sellerId);

            // add new offer count
            conn.createQuery(updateIronBuySql).addParameter("id", ironId).executeUpdate();

            IronBuyBrief ironBuyBrief = getIronBuyBrief(ironId);
            if (ironBuyBrief != null) {
                String message = generateIronBuyMessage(ironBuyBrief);
                Seller seller = SellerDataHelper.instance().getSeller(sellerId);
                if (seller != null) {
                    // 推送至 websockets
                    message = seller.companyName + "公司 已对您的" + message + "求购进行报价，请前往求购管理进行刷新查看";
                    UserMessageDataHelper.instance().setUserMessage(ironBuyBrief.userId, message);

                    // 推送至mobile
                    System.out.println("-----offerIronBuy--userId-" + ironBuyBrief.userId);
                    BuyPushData pushData = new BuyPushData(ironBuyBrief.userId, BasePushData.PUSH_TYPE_BUY);
                    pushData.title = "您的求购有新报价";
                    pushData.desc = seller.companyName + "公司 已对您的" + message + "求购进行报价，点击查看";
                    pushData.setIronBuyBrief(ironBuyBrief);
                    pushData.bageCount = pushData.newSupplyNums + getMySellerIronBuy(sellerId, ironId);
                    PageBuilder pageBuilder = new PageBuilder(0, 10)
                            .addEqualWhere("userId", ironBuyBrief.userId)
                            .addEqualWhere("status", 0);
                    pushData.newSupplyNums = IronDataHelper.getIronDataHelper().getMaxIronBuyNewSupplyNum(pageBuilder);
                    PushManager.instance().pushData(pushData);
                }

            }
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
        String sql = "select count(*) from iron_buy_supply where ironId=:ironId and sellerId=:sellerId";
        try (Connection conn = getConn()) {
            Integer count = conn.createQuery(sql)
                    .addParameter("ironId", ironId)
                    .addParameter("sellerId", sellerId).executeScalar(Integer.class);
            return count != null && count > 0;
        }
    }

    public List<IronProduct> getMyIronProduct(PageBuilder pageBuilder, String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(IronProduct.class) + " " +
                "from iron_product where userId=:userId and reviewed=true and deleteStatus=0 order by pushTime desc " + pageBuilder.generateLimit();
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
        String sql = "select count(*) from iron_product where userId=:userId and reviewed=true ";
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
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("numbers", numbers)
                    .addParameter("price", price)
                    .addParameter("title", spec)
                    .addParameter("proId", ironId).executeUpdate();
        }
    }

    public void deleteIronProduct(String ironId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "update iron_product set deleteStatus=1 where proId=:proId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("proId", ironId).executeUpdate();
            SellerDataHelper.instance().deSellerProductCount(conn, 0, ironId);
        }
    }

    public void deleteIronBuyByUser(String ironId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "update iron_buy set status=5 where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", ironId).executeUpdate();
        }
    }


    public void deleteIronBuy(String id) throws NoSuchFieldException, IllegalAccessException {
        // delete iron buy
        String sql = "delete from iron_buy where id=:id";
        // delete iron_buy_seller
        String ironBuySellerSql = "delete from iron_buy_supply where ironId=:id";
        // delete iron_buy_supply
        String ironBuySupplySql = "delete from iron_buy_seller where ironId=:id";
        // delete seller_transactions
        String ironBuyTransactionsSql = "delete from seller_transactions where productId=:id and productType=0 ";
        // delete qt
        String qtSql = "delete from iron_buy_qt where ironBuyId=:id";
        // delete iron buy miss
        String ironBuyMissSql = "delete from iron_buy_seller_miss where ironId=:id";

        String transactionSql = "select * from seller_transactions where  productId=:id and productType=0";

        try (Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(transactionSql).addParameter("id", id).executeAndFetchTable().rows();
            if (rows != null && rows.size() > 0) {
                Row row = rows.get(0);
                String sellerId = row.getString("sellerId");
                String buyerId = row.getString("buyerId");
                Float money = row.getFloat("money");
                money = money == null ? 0 : money;
                OrderDataHelper.instance().degreeIntegralByBuy(conn, buyerId, sellerId, money);
            }

            conn.createQuery(sql).addParameter("id", id).executeUpdate();
            conn.createQuery(ironBuySellerSql).addParameter("id", id).executeUpdate();
            conn.createQuery(ironBuySupplySql).addParameter("id", id).executeUpdate();
            conn.createQuery(ironBuyTransactionsSql).addParameter("id", id).executeUpdate();
            conn.createQuery(qtSql).addParameter("id", id).executeUpdate();
            conn.createQuery(ironBuyMissSql).addParameter("id", id).executeUpdate();
        }
    }

    public IronBuyOfferDetail getWinSellerOffer(String ironBuyId, String userId) {
        String sql = "select " + generateFiledString(IronBuyOfferDetail.class) + " from iron_buy_supply where ironId=:id and sellerId=:userId";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", ironBuyId).addParameter("userId", userId).executeAndFetchFirst(IronBuyOfferDetail.class);
        }
    }

    public void insertFindHelpProduct(HelpFindProduct helpFindProduct) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "help_find_product", helpFindProduct).executeUpdate();
        }
    }

    public QtDetail getQtDetail(String ironId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(QtDetail.class) + " from iron_buy_qt where ironBuyId=:id";
        try (Connection conn = getConn()) {
            QtDetail qtDetail = conn.createQuery(sql).addParameter("id", ironId).executeAndFetchFirst(QtDetail.class);
            if (qtDetail == null) {
                return null;
            } else {
                qtDetail.setIronBuyBrief(IronDataHelper.getIronDataHelper().getIronBuyBrief(ironId));
            }
            return qtDetail;
        }
    }

    public void insertQt(QtBrief qtBrief) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "iron_buy_qt", qtBrief).executeUpdate();
        }
    }

    public QtListResponse qtList(PageBuilder pageBuilder) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException, NoSuchFieldException {
        String where = pageBuilder.generateWhere();
        where = StringUtils.isEmpty(where) ? "" : " where " + where;
        String sql = "select " + generateFiledString(QtDetail.class) + " from iron_buy_qt " + where + pageBuilder.generateLimit();

        String countSql = "select count(*) from iron_buy_qt " + where;

        QtListResponse qtListResponse = new QtListResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try (Connection conn = getConn()) {
            List<QtDetail> qtDetails = conn.createQuery(sql).executeAndFetch(QtDetail.class);
            for (QtDetail qtDetail : qtDetails) {
                qtDetail.setIronBuyBrief(IronDataHelper.getIronDataHelper().getIronBuyBrief(qtDetail.ironBuyId));
            }
            qtListResponse.qts = qtDetails;
            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;
            qtListResponse.maxCount = count;
        }
        return qtListResponse;
    }

    public void voteIron(String ironId, float vote) {
        String currentScoreSql = "select score from iron_product where proId=:proId";
        String updateScoreSql = "update iron_product set score=:score where proId=:proId";
        try (Connection conn = getConn()) {
            Float currentScore = conn.createQuery(currentScoreSql).addParameter("proId", ironId).executeScalar(Float.class);
            currentScore = currentScore == null ? 0 : currentScore;
            float newScore = 0;
            if (currentScore != 0) {
                newScore = (vote + currentScore) / 2;
            } else {
                newScore = vote;
            }
            newScore = NumberUtils.round(newScore, 1);
            conn.createQuery(updateScoreSql).addParameter("score", newScore).addParameter("proId", ironId).executeUpdate();
        }
    }

    public MyBuyHistoryInfo getMyBuyHistoryInfo(String userId) {
        String todayBuySql = "select count(*) from iron_buy where pushTime<:todayEnd and pushTime>=:todayStart and userId=:userId ";
        String todayBuyDoneSql = "select count(*) from iron_buy where supplyWinTime<:todayEnd and supplyWinTime>=:todayStart and userId=:userId and status=1 ";
        String monthBuySql = "select count(*) from iron_buy where pushTime<:monthEnd and pushTime>=:monthStart and userId=:userId ";
        String monthBuyDoneSql = "select count(*) from iron_buy where supplyWinTime<:monthEnd and supplyWinTime>=:monthStart and userId=:userId and status=1 ";

        try(Connection conn = getConn()) {
            MyBuyHistoryInfo myBuyHistoryInfo = new MyBuyHistoryInfo();
            Integer todayBuyCount = conn.createQuery(todayBuySql).addParameter("todayEnd", System.currentTimeMillis())
                    .addParameter("todayStart", DateHelper.getTodayStart())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            Integer todayBuyDoneCount = conn.createQuery(todayBuyDoneSql).addParameter("todayEnd", System.currentTimeMillis())
                    .addParameter("todayStart", DateHelper.getTodayStart())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            myBuyHistoryInfo.todayBuy = todayBuyCount == null ? 0 : todayBuyCount;
            myBuyHistoryInfo.todayDone = todayBuyDoneCount == null ? 0 : todayBuyDoneCount;
            if (myBuyHistoryInfo.todayBuy != 0) {
                myBuyHistoryInfo.todayDoneRate = (float)myBuyHistoryInfo.todayDone / (float) myBuyHistoryInfo.todayBuy;
            }

            Integer monthBuyCount = conn.createQuery(monthBuySql).addParameter("monthEnd", System.currentTimeMillis())
                    .addParameter("monthStart", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            Integer monthBuyDoneCount = conn.createQuery(monthBuyDoneSql).addParameter("monthEnd", System.currentTimeMillis())
                    .addParameter("monthStart",DateHelper.getThisMonthStartTimestamp())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            myBuyHistoryInfo.monthBuy = monthBuyCount == null ? 0 : monthBuyCount;
            myBuyHistoryInfo.monthDone = monthBuyDoneCount == null ? 0 : monthBuyDoneCount;
            if (myBuyHistoryInfo.monthBuy != 0) {
                myBuyHistoryInfo.monthDoneRate = (float) myBuyHistoryInfo.monthDone / (float) myBuyHistoryInfo.monthBuy;
            }

            return myBuyHistoryInfo;
        }
    }


    public MyOfferHistoryInfo getMyOfferHistoryInfo(String userId) {
        String todayBuySql = "select count(*) from iron_buy_supply where offerTime<:todayEnd and offerTime>=:todayStart and sellerId=:userId ";
        String todayBuyDoneSql = "select count(*) from iron_buy where supplyWinTime<:todayEnd and supplyWinTime>=:todayStart and supplyUserId=:userId and status=1 ";
        String monthBuySql = "select count(*) from iron_buy_supply where offerTime<:monthEnd and offerTime>=:monthStart and sellerId=:userId ";
        String monthBuyDoneSql = "select count(*) from iron_buy where supplyWinTime<:monthEnd and supplyWinTime>=:monthStart and supplyUserId=:userId and status=1 ";

        try(Connection conn = getConn()) {
            MyOfferHistoryInfo myOfferHistoryInfo = new MyOfferHistoryInfo();
            Integer todayOfferCount = conn.createQuery(todayBuySql).addParameter("todayEnd", System.currentTimeMillis())
                    .addParameter("todayStart", DateHelper.getTodayStart())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            Integer todayOfferDoneCount = conn.createQuery(todayBuyDoneSql).addParameter("todayEnd", System.currentTimeMillis())
                    .addParameter("todayStart", DateHelper.getTodayStart())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            myOfferHistoryInfo.todayOffer = todayOfferCount == null ? 0 : todayOfferCount;
            myOfferHistoryInfo.todayWin = todayOfferDoneCount == null ? 0 : todayOfferDoneCount;
            if (myOfferHistoryInfo.todayOffer != 0) {
                myOfferHistoryInfo.todayWinRate = (float) myOfferHistoryInfo.todayWin / (float) myOfferHistoryInfo.todayOffer;
            }

            Integer monthOfferCount = conn.createQuery(monthBuySql).addParameter("monthEnd", System.currentTimeMillis())
                    .addParameter("monthStart", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            Integer monthOfferDoneCount = conn.createQuery(monthBuyDoneSql).addParameter("monthEnd", System.currentTimeMillis())
                    .addParameter("monthStart",DateHelper.getThisMonthStartTimestamp())
                    .addParameter("userId", userId).executeScalar(Integer.class);
            myOfferHistoryInfo.monthOffer = monthOfferCount == null ? 0 : monthOfferCount;
            myOfferHistoryInfo.monthWin = monthOfferDoneCount == null ? 0 : monthOfferDoneCount;
            if (myOfferHistoryInfo.monthOffer != 0) {
                myOfferHistoryInfo.monthWinRate = (float) myOfferHistoryInfo.monthWin / (float) myOfferHistoryInfo.monthOffer;
            }

            return myOfferHistoryInfo;
        }
    }

    public MyAllHistoryInfo getMyAllHistoryInfo(String userId) {
        String buyTimesSql = "select count(*) from iron_buy where userId=:userId ";
        String buyWinTimesSql = "select count(*) from iron_buy where userId=:userId and status=1 ";
        String offerTimesSql = "select count(*) from iron_buy_supply where sellerId=:userId ";
        String offerWinSql = "select count(*) from iron_buy where supplyUserId=:userId and status=1 ";

        try(Connection conn = getConn()) {
            MyAllHistoryInfo myOfferHistoryInfo = new MyAllHistoryInfo();
            Integer buyTimes = conn.createQuery(buyTimesSql)
                    .addParameter("userId", userId).executeScalar(Integer.class);
            Integer buyWinTimes = conn.createQuery(buyWinTimesSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOfferHistoryInfo.buyTimes = buyTimes == null ? 0 : buyTimes;
            myOfferHistoryInfo.buyWinTimes = buyWinTimes == null ? 0 : buyWinTimes;

            Integer offerTimes = conn.createQuery(offerTimesSql).addParameter("userId", userId).executeScalar(Integer.class);
            Integer offerWinTimes = conn.createQuery(offerWinSql).addParameter("userId", userId).executeScalar(Integer.class);
            myOfferHistoryInfo.offerTimes = offerTimes == null ? 0 : offerTimes;
            myOfferHistoryInfo.offerWinTimes = offerWinTimes == null ? 0 : offerWinTimes;
            return myOfferHistoryInfo;
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
        public String unit;
        public String supplyMsg;
    }

    public static class UserBuyInfo {
        public int buyTimes;
        public float buySuccessRate;
    }


}
