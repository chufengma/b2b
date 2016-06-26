package onefengma.demo.server.services.user;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.apibeans.product.ShopDetailRequest;
import onefengma.demo.server.model.product.ShopBrief;
import onefengma.demo.server.model.product.ShopDetail;
import sun.rmi.runtime.Log;

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
                "@ALL_CER, @BUSINESS_LIC, @CODE_LIC, @FINANCE_LIC) values (" +
                ":userId, :companyName, :regMoney, :contact, :cantactTel," +
                ":fax, :cityId, :officeAddress, :qq, :shopProfile, :allCer," +
                ":buCli, :codeCli, :finCli)");
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
                    .executeUpdate();
        }
    }

    public Seller getSellerByUserId(String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select * from @TABLE where @USER_ID = :userId;");
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
        String sql = "select " + generateFiledString(ShopBrief.class) + " from seller, shop_orders " +
                "where userId = sellerId order by ironCount desc limit 0,10";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(ShopBrief.class);
        }
    }

    public int getShopCount(PageBuilder pageBuilder) {
        String sql = "select count(*)" + " from seller, shop_orders " +
                "where userId = sellerId " + generateWhereKey(pageBuilder, false);
        try (Connection connection = getConn()){
            return connection.createQuery(sql).executeScalar(Integer.class);
        }
    }

    public List<ShopBrief> getShops(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(ShopBrief.class) + " from seller, shop_orders " +
                "where userId = sellerId " + generateWhereKey(pageBuilder, true);
        try (Connection connection = getConn()){
            return connection.createQuery(sql).executeAndFetch(ShopBrief.class);
        }
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
        String sql = "select " + generateFiledString(ShopBrief.class) + " from seller, shop_orders " +
                "where userId = sellerId order by handingCount limit 0, 10";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).executeAndFetch(ShopBrief.class);
        }
    }

    public ShopDetail getShopDetail(String userId) {
        String sql = "select " + generateFiledString(ShopDetail.class)
                + " from seller,shop_orders where userId = sellerId and userId=:userId";
        LogUtils.i("---getShopDetail sql--" + sql);
        try(Connection conn = getConn()) {
            List<ShopDetail> shopDetails = conn.createQuery(sql)
                    .addParameter("userId", userId).executeAndFetch(ShopDetail.class);
            if (!shopDetails.isEmpty()) {
                return shopDetails.get(0);
            } else {
                return null;
            }
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
}
