package onefengma.demo.server.services.user;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.Seller;

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
    
}
