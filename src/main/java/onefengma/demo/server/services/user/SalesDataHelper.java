package onefengma.demo.server.services.user;

import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.sales.SalesManSellerResponse;
import onefengma.demo.server.model.apibeans.sales.SalesManUserResponse;
import onefengma.demo.server.services.funcs.CityDataHelper;
import org.sql2o.Connection;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/9/1
 */
public class SalesDataHelper extends BaseDataHelper {

    private static SalesDataHelper instance;

    public static SalesDataHelper instance() {
        if (instance == null) {
            instance = new SalesDataHelper();
        }
        return instance;
    }

    public SalesManDetail getSalesMan(String tel) {
        String sql = "select * from salesman where tel=:tel";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("tel", tel).executeAndFetchFirst(SalesManDetail.class);
        }
    }

    public SalesManUserResponse getBindUserResponse(String salesId, PageBuilder pageBuilder) {
        String userSql = "select " + generateFiledString(UserInfo.class) + " from user where userId not in (select userId from seller) and salesManId=:salesId"
                + pageBuilder.generateWherePlus(false)
                + " order by salesBindTime desc " + pageBuilder.generateLimit();

        String countSql = "select count(*) from user where  userId not in (select userId from seller) and salesManId=:salesId"
                + pageBuilder.generateWherePlus(false);

        try(Connection conn = getConn()) {
            SalesManUserResponse salesManUserResponse = new SalesManUserResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            salesManUserResponse.userInfos = conn.createQuery(userSql).addParameter("salesId", salesId).executeAndFetch(UserInfo.class);
            Integer count = conn.createQuery(countSql).addParameter("salesId", salesId).executeScalar(Integer.class);
            count = count == null ? 0 : count;
            salesManUserResponse.maxCount = count;
            return salesManUserResponse;
        }
    }

    public SalesManSellerResponse getBindSellerResponse(String salesId, PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String userSql = "select " + generateFiledString(SellerInfo.class) + " from seller,user where user.userId=seller.userId " +
                " and user.salesManId=:salesId"
                + pageBuilder.generateWherePlus(false)
                + " order by salesBindTime desc " + pageBuilder.generateLimit();

        String countSql = "select count(*) from seller,user where user.userId=seller.userId and user.salesManId=:salesId"
                + pageBuilder.generateWherePlus(false);

        try(Connection conn = getConn()) {
            SalesManSellerResponse sellerResponse = new SalesManSellerResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            sellerResponse.sellerInfos = conn.createQuery(userSql).addParameter("salesId", salesId).executeAndFetch(SellerInfo.class);
            if (sellerResponse.sellerInfos != null) {
                for(SellerInfo sellerInfo : sellerResponse.sellerInfos) {
                    sellerInfo.setLocateCity(CityDataHelper.instance().getCityDescById(sellerInfo.cityId));
                }
            }

            Integer count = conn.createQuery(countSql).addParameter("salesId", salesId).executeScalar(Integer.class);
            count = count == null ? 0 : count;
            sellerResponse.maxCount = count;
            return sellerResponse;
        }
    }

    public static class SalesManDetail {
        public int id;
        public String name;
        public String tel;
        public String password;
        public long bindTime;
    }

    public static class UserInfo {
        public String name;
        public String mobile;
    }

    public static class SellerInfo {
        public String contact;
        public String mobile;
        public String companyName;
        public String cityId;
        private String locateCity;

        public String getLocateCity() {
            return locateCity;
        }

        public void setLocateCity(String locateCity) {
            this.locateCity = locateCity;
        }
    }

}
