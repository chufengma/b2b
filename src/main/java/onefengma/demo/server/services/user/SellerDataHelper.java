package onefengma.demo.server.services.user;

import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.UpdateBuilder;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.product.HandingBuyBrief;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.ShopBrief;
import onefengma.demo.server.model.product.ShopDetail;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;

/**
 * Created by chufengma on 16/6/2.
 */
public class SellerDataHelper extends BaseDataHelper {

    private static final String TABLE = "seller";
    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String COMPANY_NAME = "companyName";
    private static final String REG_MONEY = "regMoney";
    private static final String CONTACT = "contact";
    private static final String CANTACT_TEL = "cantactTel";
    private static final String FAX = "fax";
    private static final String CITY_ID = "cityId";
    private static final String OFFICE_ADDRESS = "officeAddress";
    private static final String QQ = "qq";
    private static final String SHOP_PROFILE = "shopProfile";
    private static final String ALL_CER = "allCer";
    private static final String BUSINESS_LIC = "businessLic";
    private static final String CODE_LIC = "codeLic";
    private static final String FINANCE_LIC = "financeLic";

    private static SellerDataHelper sellerDataHelper;

    public static SellerDataHelper instance() {
        if (sellerDataHelper == null) {
            sellerDataHelper = new SellerDataHelper();
        }
        return sellerDataHelper;
    }

    public void insertSeller(Seller seller) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("insert into @TABLE(@USER_ID, @COMPANY_NAME, @REG_MONEY, @CONTACT," +
                "@CANTACT_TEL, @FAX, @CITY_ID, @OFFICE_ADDRESS, @QQ, @SHOP_PROFILE, " +
                "@ALL_CER, @BUSINESS_LIC, @CODE_LIC, @FINANCE_LIC, applyTime) values (" +
                ":userId, :companyName, :regMoney, :contact, :cantactTel," +
                ":fax, :cityId, :officeAddress, :qq, :shopProfile, :allCer," +
                ":buCli, :codeCli, :finCli, :applyTime)");
        try(Connection connection = getConn()) {
            connection.createQuery(sql).addParameter("userId", seller.userId)
                    .addParameter("companyName", seller.companyName)
                    .addParameter("regMoney", seller.regMoney)
                    .addParameter("contact", seller.contact)
                    .addParameter("cantactTel", seller.cantactTel)
                    .addParameter("fax", seller.fax)
                    .addParameter("cityId", seller.cityId)
                    .addParameter("officeAddress", seller.officeAddress)
                    .addParameter("qq", seller.qq)
                    .addParameter("shopProfile", seller.shopProfile)
                    .addParameter("allCer", seller.allCer)
                    .addParameter("buCli", seller.businessLic)
                    .addParameter("codeCli", seller.codeLic)
                    .addParameter("finCli", seller.financeLic)
                    .addParameter("applyTime", seller.applyTime)
                    .executeUpdate();
        }
    }

    public Seller getSellerByUserId(String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select " + generateFiledString(Seller.class) +  " from @TABLE where @USER_ID = :userId and passed=true");
        try (Connection conn = getConn()){
            List<Seller> sellerList = conn.createQuery(sql).addParameter("userId", userId).executeAndFetch(Seller.class);
            if (sellerList.isEmpty()) {
                return null;
            } else {
                return sellerList.get(0);
            }
        }
    }

    public boolean isSeller(String userID) throws NoSuchFieldException, IllegalAccessException {
        return getSellerByUserId(userID) == null ? false : true;
    }

    public List<ShopBrief> searchShops(String keyword, PageBuilder pageBuilder) {
        keyword = "%" + keyword + "%";
        String sql = "select " + generateFiledString(ShopBrief.class) + " from seller, shop_orders " +
                "where userId = sellerId and companyName like '" + keyword + "'" +
                " or officeAddress like '" + keyword + "'" +
                " or shopProfile like '" + keyword + "'" + pageBuilder.generateLimit();
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(ShopBrief.class);
        }
    }

    public int searchShopCount(String keyword) {
        keyword = "%" + keyword + "%";
        String sql = "select count(*) from seller,shop_orders " +
                "where userId=sellerId and companyName like '" + keyword + "'" +
                " or officeAddress like '" + keyword + "'" +
                " or shopProfile like '" + keyword + "'";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<ShopBrief> getRecommendShopsByIron() {
        String sql = "select * from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions where productType = 0 and finishTime < :endTime and finishTime >= :startTime " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId where passed=true " +
                "order by count desc limit 0, 10 ";
            try(Connection conn = getConn()) {
                List<Row> rows = conn.createQuery(sql)
                        .addParameter("startTime", DateHelper.getLastMonthStartTimestamp())
                        .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                        .executeAndFetchTable().rows();
                List<ShopBrief> shopBriefs = new ArrayList<>();
                for(Row row : rows) {
                    shopBriefs.add(generateShopByRow(row, 0));
                }
                return shopBriefs;
            }
    }

    public ShopBrief getShopBrief(String userId) {
        String sql = "select * from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions where finishTime < :endTime and finishTime >= :startTime " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId where seller.userId = :userId " +
                "order by count desc limit 0, 10";
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql)
                    .addParameter("userId", userId)
                    .addParameter("startTime", DateHelper.getLastMonthStartTimestamp())
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                    .executeAndFetchTable().rows();
            List<ShopBrief> shopBriefs = new ArrayList<>();
            for(Row row : rows) {
                return generateShopByRow(row, -1);
            }
            return null;
        }
    }

    public ShopBrief generateShopByRow(Row row, int productType) {
        ShopBrief shopBrief = new ShopBrief();
        shopBrief.userId = row.getString("userId");
        shopBrief.companyName = row.getString("companyName");
        shopBrief.cover = row.getString("cover");

        if (productType == 0) {
            shopBrief.ironCount = (row.getFloat("count") == null ? 0 : row.getFloat("count"));
            shopBrief.ironMoney = row.getFloat("money") == null ? 0 : row.getFloat("money");
            shopBrief.ironTypeDesc = row.getString("ironTypeDesc");
        } else if (productType == 1){
            shopBrief.handingCount = (row.getFloat("count") == null ? 0 : row.getFloat("count"));
            shopBrief.handingMoney = row.getFloat("money") == null ? 0 : row.getFloat("money");
            shopBrief.handingTypeDesc = row.getString("handingTypeDesc");
        } else {
            shopBrief.count = (row.getFloat("count") == null ? 0 : row.getFloat("count"));
            shopBrief.money = row.getFloat("money") == null ? 0 : row.getFloat("money");
            shopBrief.handingTypeDesc = row.getString("handingTypeDesc");
            shopBrief.ironTypeDesc = row.getString("ironTypeDesc");
        }
        shopBrief.productNumbers = row.getInteger("productCount");
        shopBrief.officeAddress = row.getString("officeAddress");
        shopBrief.score = row.getFloat("score") == null ? 0 : row.getFloat("score");
        return shopBrief;
    }

    public int getShopCount(PageBuilder pageBuilder, int productType) {
        String sql =  "select count(*) from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId " +
                "where passed = true " + genereateProductTypeSql(productType) +  generateWhereKey(pageBuilder, false);
        try (Connection connection = getConn()){
            Integer count =  connection.createQuery(sql).executeScalar(Integer.class);
            return count == 0 ? 0 : count;
        }
    }

    public List<ShopBrief> getShops(PageBuilder pageBuilder, int productType) {
        String productTypeSql = "";
        if (productType == 0 || productType == 1) {
            productTypeSql = " productType=" + productType + " and ";
        };

        String sql = "select * from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions where " + productTypeSql + " finishTime < :endTime and finishTime >= :startTime " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId " +
                "where passed = true "
                + genereateProductTypeSql(productType)
                + generateWhereKey(pageBuilder, true);
        try (Connection connection = getConn()){
            List<Row> rows = connection.createQuery(sql)
                    .addParameter("startTime", DateHelper.getThisMonthStartTimestamp())
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp()).executeAndFetchTable().rows();
            List<ShopBrief> shopBriefs = new ArrayList<>();
            for (Row row : rows) {
                shopBriefs.add(generateShopByRow(row, productType));
            }
            return shopBriefs;
        }
    }

    public String genereateProductTypeSql(int productType) {
        if (productType == 0) {
            return " and ironTypeDesc <> '' ";
        }
        if (productType == 1) {
            return " and handingTypeDesc <> '' ";
        }
        return "";
    }

    private String generateWhereKey(PageBuilder pageBuilder, boolean withLimit) {
        StringBuilder whereBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(pageBuilder.keyword)) {
            whereBuilder.append(generateKeyword(pageBuilder.keyword));
        }
        whereBuilder.append(pageBuilder.hasWhere() ? " and " : "");
        whereBuilder.append(pageBuilder.generateSql(withLimit));
        return whereBuilder.toString();
    }

    private String generateKeyword(String keyword) {
        return " and companyName like '%" + keyword + "%'" +
        " or officeAddress like '%" + keyword + "%'" +
                " or shopProfile like '%" + keyword + "%'";
    }


    public List<ShopBrief> getRecommendShopsByHanding() {
        String sql = "select * from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions where productType = 1 and finishTime < :endTime and finishTime >= :startTime " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId where passed=true " +
                "order by count desc limit 0, 10 ";

        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql)
                    .addParameter("startTime", DateHelper.getLastMonthStartTimestamp())
                    .addParameter("endTime", DateHelper.getNextMonthStatimestamp())
                    .executeAndFetchTable().rows();
            List<ShopBrief> shopBriefs = new ArrayList<>();
            for(Row row : rows) {
                ShopBrief shopBrief = new ShopBrief();
                shopBrief.userId = row.getString("userId");
                shopBrief.companyName = row.getString("companyName");
                shopBrief.cover = row.getString("cover");
                shopBrief.handingCount = (row.getFloat("count") == null ? 0 : row.getFloat("count"));
                shopBrief.handingMoney = row.getFloat("money") == null ? 0 : row.getFloat("money");
                shopBrief.handingTypeDesc = row.getString("handingTypeDesc");
                shopBrief.officeAddress = row.getString("officeAddress");
                shopBrief.score = row.getFloat("score") == null ? 0 : row.getFloat("score");
                shopBriefs.add(shopBrief);
            }
            return shopBriefs;
        }
    }

    public ShopBrief getShopByProId(String proId, int proType) {
        String sqlUser = "";
        if (proType == 0) {
            sqlUser = "select userId from iron_product where proId=:proId";
        } else if (proType == 1) {
            sqlUser = "select userId from handing_product where id=:proId";
        } else {
            return null;
        }

        String sqlShop = "select * from seller where seller.userId=:userId";
        String transSql = "select sum(money) as money , sum(count) as count " +
                "from seller_transactions where sellerId=:userId  ";

        try(Connection conn = getConn()) {
            String userId = conn.createQuery(sqlUser).addParameter("proId", proId).executeScalar(String.class);
            List<Row> rows = conn.createQuery(sqlShop).addParameter("userId", userId).executeAndFetchTable().rows();
            for(Row row : rows) {
                ShopBrief shopBrief = new ShopBrief();
                shopBrief.userId = row.getString("userId");
                shopBrief.companyName = row.getString("companyName");
                shopBrief.cover = row.getString("cover");

                List<Row> handinRows = conn.createQuery(transSql).addParameter("userId", shopBrief.userId).executeAndFetchTable().rows();
                if (!handinRows.isEmpty()) {
                    Row handingRow = handinRows.get(0);
                    shopBrief.money = (handingRow.getFloat("money") == null ? 0 : handingRow.getFloat("money"));
                    shopBrief.count = (handingRow.getFloat("count") == null ? 0 : handingRow.getFloat("count"));
                }

                shopBrief.handingTypeDesc = row.getString("handingTypeDesc");
                shopBrief.officeAddress = row.getString("officeAddress");
                shopBrief.score = row.getFloat("score") == null ? 0 : row.getFloat("score");
                return shopBrief;
            }
        }
        return null;
    }

    public ShopDetail getShopDetail(String userId) {
        String sql = "select * from seller left join " +
                "(select sellerId, sum(count) as count, sum(money) as money " +
                "from seller_transactions " +
                "group by sellerId) as trans " +
                "on seller.userId = trans.sellerId " +
                "where seller.userId = :userId";
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql)
                    .addParameter("userId", userId).executeAndFetchTable().rows();
            if (!rows.isEmpty()) {
                Row row = rows.get(0);
                ShopDetail shopDetail = new ShopDetail();
                shopDetail.userId = row.getString("userId");
                shopDetail.companyName = row.getString("companyName");
                shopDetail.cover = row.getString("cover");
                shopDetail.count = (row.getFloat("count") == null ? 0 : row.getFloat("count"));
                shopDetail.money = row.getFloat("money") == null ? 0 : row.getFloat("money");
                shopDetail.handingTypeDesc = row.getString("handingTypeDesc");
                shopDetail.ironTypeDesc = row.getString("ironTypeDesc");
                shopDetail.productNumbers = row.getInteger("productCount");
                shopDetail.officeAddress = row.getString("officeAddress");
                shopDetail.score = row.getFloat("score") == null ? 0 : row.getFloat("score");
                shopDetail.regMoney = row.getInteger("regMoney");
                shopDetail.shopProfile = row.getString("shopProfile");
                return shopDetail;
            }
            return null;
        }
    }

    public void addIronType(String userId, String type) {
        String sqlSelect = "select ironTypeDesc from seller where userId=:userId";
        String sqlUpdate = "update seller set ironTypeDesc = concat(ironTypeDesc, :newValue) where userId=:userId";
        try(Connection conn = getConn()) {
            String oldValue = conn.createQuery(sqlSelect).addParameter("userId", userId).executeScalar(String.class);
            StringBuilder newValue = new StringBuilder();
            if (!StringUtils.isEmpty(oldValue)) {
                if (oldValue.contains(type)){
                    return;
                }
                newValue.append(",");
            }
            newValue.append(type);

            conn.createQuery(sqlUpdate)
                    .addParameter("newValue", newValue.toString())
                    .addParameter("userId", userId)
                    .executeUpdate();
        }
    }

    public void addHandingType(String userId, String type) {
        String sqlSelect = "select handingTypeDesc from seller where userId=:userId";
        String sqlUpdate = "update seller set handingTypeDesc = concat(handingTypeDesc, :newValue) where userId=:userId";
        try(Connection conn = getConn()) {
            String oldValue = conn.createQuery(sqlSelect).addParameter("userId", userId).executeScalar(String.class);
            StringBuilder newValue = new StringBuilder();
            if (!StringUtils.isEmpty(oldValue)) {
                if (oldValue.contains(type)){
                    return;
                }
                newValue.append(",");
            }
            newValue.append(type);

            conn.createQuery(sqlUpdate)
                    .addParameter("newValue", newValue.toString())
                    .addParameter("userId", userId)
                    .executeUpdate();
        }
    }

    public String getBuyUserId(String proId, int productType) {
        String sql = "select userId from " + (productType == 0 ? "iron_buy" : "handing_buy") + " where id=:id";
        try(Connection conn =getConn()) {
            return conn.createQuery(sql)
                    .addParameter("id", proId)
                    .executeScalar(String.class);
        }
    }

    public Seller getSeller(String sellerId) {
        String sql = "select " + generateFiledString(Seller.class) + " from seller where userId=:userId and passed=true ";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("userId", sellerId).executeAndFetchFirst(Seller.class);
        }
    }

    public void updateSeller(UpdateBuilder updateBuilder, String userId) {
        String updateSql = updateBuilder.generateSql();

        if (StringUtils.isEmpty(updateSql)) {
            return;
        }

        String sql = "update seller set " + updateSql + "where userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
        }
    }

    public float getSellerIntegral(String userId) {
        String sql = "select integral from seller where userId=:userId";
        try(Connection conn = getConn()) {
            Float integral = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Float.class);
            return integral == null ? 0 : integral;
        }
    }

    public List<String> getUserIdsByCompanyName(String companyName) {
        String sql = "select userId from seller where companyName like '%" + companyName + "%'  and passed=true ";
        try(Connection conn = getConn()) {
            return  conn.createQuery(sql).executeAndFetch(String.class);
        }
    }

    public void updateSellerProductCount(Connection conn, int productType, String proId) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select seller set productCount=(productCount+1) where userId=:userId";
        String userId = "";
        if (productType == 0) {
            IronBuyBrief buyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(proId);
            if (buyBrief != null) {
                userId = buyBrief.userId;
            }
        } else {
            HandingBuyBrief handingBuyBrief = HandingDataHelper.getHandingDataHelper().getHandingBrief(proId);
            if (handingBuyBrief != null) {
                userId = handingBuyBrief.userId;
            }
        }
        conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
    }
}
