package onefengma.demo.server.services.user;

import org.sql2o.Connection;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.PushManager;
import onefengma.demo.server.model.apibeans.qt.QtListResponse;
import onefengma.demo.server.model.apibeans.sales.SalesIronBuysResponse;
import onefengma.demo.server.model.apibeans.sales.SalesManSellerResponse;
import onefengma.demo.server.model.apibeans.sales.SalesManUserResponse;
import onefengma.demo.server.model.mobile.IronQtPushData;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.qt.QtDetail;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;

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

    public UserInfo getUserInfo(String userId) {
        String sql = "select " + generateFiledString(UserInfo.class) + " from user where userId=:userId";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("userId", userId).executeAndFetchFirst(UserInfo.class);
        }
    }

    public SalesManUserResponse getBindUserResponse(String salesId, PageBuilder pageBuilder) {
        String userSql = "select " + generateFiledString(UserInfo.class) + " from user where userId not in (select userId from seller) and salesManId=:salesId and user.isMock = 0 "
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
                " and user.salesManId=:salesId and user.isMock = 0 "
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

    public QtListResponse qtList(PageBuilder pageBuilder) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException, NoSuchFieldException {
        String sql = "select " + generateFiledString(QtDetail.class) + " from iron_buy_qt " + pageBuilder.generateWherePlus(true) + pageBuilder.generateLimit();
        String countSql = "select count(*) from iron_buy_qt " + pageBuilder.generateWherePlus(true);

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

    public QtDetail getQtDetail(String ironId) {
        String sql = "select " + generateFiledString(QtDetail.class) + " from iron_buy_qt where ironBuyId=:ironId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("ironId", ironId).executeAndFetchFirst(QtDetail.class);
        }
    }

    public QtDetail getQtDetailByQtId(String qtId) {
        String sql = "select " + generateFiledString(QtDetail.class) + " from iron_buy_qt where qtId=:qtId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("qtId", qtId).executeAndFetchFirst(QtDetail.class);
        }
    }
    public void updateQtStatus(String qtId, int status) throws NoSuchFieldException, IllegalAccessException {
        String updateStr = " ";
        if (status == 3) {
            updateStr = ", startTime=" + System.currentTimeMillis();
        } else if (status == 1 || status == 2) {
            updateStr = ", finishTime=" + System.currentTimeMillis();
        }
        String sql = "update iron_buy_qt set status=:status " + updateStr + " where qtId=:qtId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("qtId", qtId)
                    .addParameter("status", status).executeUpdate();

            QtDetail qtDetail = getQtDetailByQtId(qtId);
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(qtDetail.ironBuyId);
            IronQtPushData ironQtPushData = new IronQtPushData(ironBuyBrief.userId, qtDetail.qtId);
            String statusStr = "";
            if (status == 3) {
                statusStr = "开始质检";
            } else if (status == 2) {
                statusStr = "已取消质检";
            } else {
                statusStr = "已完成质检";
            }
            ironQtPushData.title = "您的求购" + statusStr;
            ironQtPushData.desc = "您的求购" + IronDataHelper.getIronDataHelper().generateIronBuyMessage(ironBuyBrief);
            PushManager.instance().pushData(ironQtPushData);
        }
    }

    public SalesIronBuysResponse getSalesIronBuy(String salesId, PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        long startTime = System.currentTimeMillis();
        String sql = "select " + generateFiledString(IronBuyBrief.class) + " from iron_buy where userId in (select userId from user where salesManId=:salesId) " + pageBuilder.generateWherePlus(false) + " order by pushTime desc " + pageBuilder.generateLimit();
        String countSql = "select count(*) from iron_buy where userId in (select userId from user where salesManId=:salesId) " + pageBuilder.generateWherePlus(false) + " order by pushTime desc ";
        String supplyCountSql = "select count(*) from iron_buy_supply where ironId=:ironId";

        try(Connection conn = getConn()) {
            SalesIronBuysResponse salesIronBuysResponse = new SalesIronBuysResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            List<IronBuyBrief> ironBuyBriefs = conn.createQuery(sql).addParameter("salesId", salesId).executeAndFetch(IronBuyBrief.class);
            for (IronBuyBrief ironBuyBrief : ironBuyBriefs) {
                Integer count = conn.createQuery(supplyCountSql).addParameter("ironId", ironBuyBrief.id).executeScalar(Integer.class);
                count = count == null ? 0 : count;
                ironBuyBrief.setSupplyCount(count);
                ironBuyBrief.setSourceCity(CityDataHelper.instance().getCityDescById(ironBuyBrief.locationCityId));
            }
            salesIronBuysResponse.buys = ironBuyBriefs;

            Integer maxCount = conn.createQuery(countSql).addParameter("salesId", salesId).executeScalar(Integer.class);
            maxCount = maxCount == null ? 0 : maxCount;
            salesIronBuysResponse.maxCount = maxCount;

            LogUtils.saveToFiles("getSalesIronBuy mysql time: " + (System.currentTimeMillis() - startTime) + " ms", true);
            return salesIronBuysResponse;
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
