package onefengma.demo.server.services.user;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.admin.AdminSellersResponse;
import onefengma.demo.server.model.admin.AdminUsersResponse;
import onefengma.demo.server.model.apibeans.admin.AdminOrdersResponse;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import org.sql2o.Connection;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chufengma on 16/7/2.
 */
public class AdminDataManager extends BaseDataHelper {

    private static AdminDataManager adminDataManager;

    public static AdminDataManager instance() {
        if (adminDataManager == null) {
            adminDataManager = new AdminDataManager();
        }
        return adminDataManager;
    }

    public Admin getAdmin(String userName) {
        String sql = "select * from admin_user where userName=:userName";
        try (Connection conn = getConn()) {
            List<Admin> adminList = conn.createQuery(sql).addParameter("userName", userName).executeAndFetch(Admin.class);
            if (adminList.isEmpty()) {
                return null;
            }
            return adminList.get(0);
        }
    }

    public void deleteUser(String userId) throws Exception {
        String sql = "delete from user where userId=:userId";
        String bakSql = "insert into user_bak select * from user where userId=:userId";

        try (Connection conn = getConn()) {
            conn.createQuery(bakSql).addParameter("userId", userId).executeUpdate();
            conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
        }
    }

    public void updateUser(String userId, int salesmanId) {
        String sql = "update user set salesManId=:salesManId where userId=:userId";

        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId).addParameter("salesManId", salesmanId).executeUpdate();
        }
    }

    public AdminUsersResponse getBuyer(PageBuilder pageBuilder) {
        String whereSql = pageBuilder.generateWhere();
        String maxCountSql = "select count(*) from user,salesman where salesman.id=buySellerId " + ((StringUtils.isEmpty(whereSql)) ? "" : " and " + whereSql);
        String userSalesMoneySql = "select userId, mobile,registerTime, sum(ironMoney + handingMoney)  as buyMoney , tel as salesTel, salesmanId as salesId " +
                "from (select userId, mobile,registerTime,salesmanId,tel from user,salesman where salesmanId=salesman.id) as userComplete" +
                " left join buyer_amount on (userId=buyerId and day <=:endTime and day>:startTime ) "
                + (StringUtils.isEmpty(pageBuilder.generateWhere()) ? "" : " where " + whereSql)
                + " group by userId  order by buyMoney desc" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            AdminUsersResponse usersResponse = new AdminUsersResponse();
            usersResponse.buyers = conn.createQuery(userSalesMoneySql)
                    .addParameter("startTime", pageBuilder.startTime)
                    .addParameter("endTime", pageBuilder.endTime)
                    .executeAndFetch(BuyerBrief.class);
            Long maxCount = conn.createQuery(maxCountSql).executeScalar(Long.class);
            usersResponse.maxCount = (maxCount == null ? 0 : maxCount);
            return usersResponse;
        }
    }

    public AdminSellersResponse getSellers(PageBuilder pageBuilder, long dataStartTime, long dateEndTime, boolean timeByBuy, String companyName) {
        String whereSql = pageBuilder.generateWhere();
        String amountTable = "";
        String totalMoneyKeyName = "";

        if (timeByBuy) {
            amountTable = "buyer_amount";
            totalMoneyKeyName = "buyerTotalMoney";
        } else {
            amountTable = "seller_amount";
            totalMoneyKeyName = "sellerTotalMoney";
        }

        String companySql = StringUtils.isEmpty(companyName) ? "" :  "and companyName like '%" + companyName +"%' ";

        String maxCountSql = "select count(*) from user,seller,salesman where user.userId=seller.userId and user.salesManId=salesman.id "
                + " and registerTime<:registerEndTime and registerTime>=:registerStartTime "
                + ((StringUtils.isEmpty(whereSql)) ? "" : " and " + whereSql);
        String sellerSql = "select userId,companyName,passTime as becomeSellerTime,contact as contactName,productCount,score, mobile,registerTime, sum(ironMoney + handingMoney)  as " + totalMoneyKeyName + " , tel as salesMobile,salesId " +
                "from (select companyName,user.userId,passTime,contact, productCount,score,mobile,registerTime,user.salesManId as salesId, tel " +
                "from user,salesman,seller where user.salesManId=salesman.id and user.userId=seller.userId) " +
                "as userComplete left join " + amountTable + " " +
                "on (userId=sellerId and day>=:dataStartTime and day<:dataEndTime) " +
                " where registerTime<:registerEndTime and registerTime>=:registerStartTime " + companySql +
                (StringUtils.isEmpty(pageBuilder.generateWhere()) ? "" : " where " + whereSql) +
                "group by userId " +
                "order by " + totalMoneyKeyName + " desc " + pageBuilder.generateLimit();

        AdminSellersResponse adminSellersResponse = new AdminSellersResponse();
        adminSellersResponse.pageCount = pageBuilder.pageCount;
        adminSellersResponse.currentPage = pageBuilder.currentPage;
        try(Connection conn = getConn()) {
            adminSellersResponse.sellers = conn.createQuery(sellerSql)
                    .addParameter("dataStartTime", dataStartTime)
                    .addParameter("dataEndTime", dateEndTime)
                    .addParameter("registerStartTime", pageBuilder.startTime)
                    .addParameter("registerEndTime", pageBuilder.endTime)
                    .executeAndFetch(SellerBrief.class);

            Integer maxCount = conn.createQuery(maxCountSql)
                    .addParameter("registerStartTime", pageBuilder.startTime)
                    .addParameter("registerEndTime", pageBuilder.endTime)
                    .executeScalar(Integer.class);

            adminSellersResponse.maxCount = maxCount == null ? 0 : maxCount;
        }
        return adminSellersResponse;
    }


    public AdminOrdersResponse getOrdersForAdmin(PageBuilder pageBuilder) {
        String sql = "select * from product_orders " + (pageBuilder.hasWhere() ? " where " : " ") + pageBuilder.generateWhere() + " " + pageBuilder.generateLimit();
        String countSql = "select count(*) from product_orders " + (pageBuilder.hasWhere() ? " where " : " ") + pageBuilder.generateWhere();

        AdminOrdersResponse adminOrdersResponse = new AdminOrdersResponse();
        adminOrdersResponse.currentPage = pageBuilder.currentPage;
        adminOrdersResponse.pageCount = pageBuilder.pageCount;
        List<OrderForAdmin> orderForAdmins = new ArrayList<>();
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for (Row row : rows) {
                OrderForAdmin orderForAdmin = new OrderForAdmin();
                orderForAdmin.orderId = row.getString("id");
                int productType = row.getInteger("productType");

                orderForAdmin.count = row.getFloat("count");
                if (productType == 0) {
                    orderForAdmin.price = IronDataHelper.getIronDataHelper().getIronPrice(orderForAdmin.orderId);
                } else {
                    orderForAdmin.price = HandingDataHelper.getHandingDataHelper().getHandingPrice(orderForAdmin.orderId);
                }
                orderForAdmin.totalMoney = row.getFloat("totalMoney");
                orderForAdmin.pushTime = row.getLong("sellTime");
                orderForAdmin.outDateTime = orderForAdmin.pushTime + row.getLong("timeLimit");
                orderForAdmin.finishTime = row.getLong("finishTime");
                orderForAdmin.status = row.getInteger("status");
                orderForAdmin.salesManId = row.getInteger("salesmanId");

                String buyerId = row.getString("buyerId");
                orderForAdmin.buyerMobile = UserDataHelper.instance().getUserMobile(buyerId);

                String sellerId = row.getString("sellerId");
                Seller seller = SellerDataHelper.instance().getSeller(sellerId);
                if (seller != null) {
                    orderForAdmin.sellerMobile = UserDataHelper.instance().getUserMobile(sellerId);
                    orderForAdmin.sellerCompany = seller.companyName;
                }

                SalesMan salesMan = UserDataHelper.instance().getSalesManById(orderForAdmin.salesManId);
                if (salesMan != null) {
                    orderForAdmin.salesManMobile = salesMan.tel;
                }
                orderForAdmins.add(orderForAdmin);
            }

            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;

            adminOrdersResponse.maxCount = count;
            adminOrdersResponse.orders = orderForAdmins;
        }
        return adminOrdersResponse;
    }

    public static class OrderForAdmin {
        public String orderId;
        public String buyerMobile;
        public String sellerMobile;
        public String sellerCompany;
        public float price;
        public float count;
        public float totalMoney;
        public long pushTime;
        public long outDateTime;
        public long finishTime;
        public int status;
        public int salesManId;
        public String salesManMobile;
    }

    public static class SellerBrief {
        public String userId;
        public String mobile;
        public String companyName;
        public long registerTime;
        public long becomeSellerTime;
        public String contactName;
        public int productCount;
        public float score;
        public int salesId;
        public String salesMobile;

        private float sellerTotalMoney;
        private float buyerTotalMoney;

        public float getSellerTotalMoney() {
            return sellerTotalMoney;
        }

        public void setSellerTotalMoney(float sellerTotalMoney) {
            this.sellerTotalMoney = sellerTotalMoney;
        }

        public float getBuyerTotalMoney() {
            return buyerTotalMoney;
        }

        public void setBuyerTotalMoney(float buyerTotalMoney) {
            this.buyerTotalMoney = buyerTotalMoney;
        }
    }

    public static class BuyerBrief {
        public String mobile;
        private long registerTime;
        public int salesId;
        public String salesTel;
        public float buyMoney;
        public String userId;

        public long getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(long registerTime) {
            this.registerTime = registerTime;
        }
    }

}
