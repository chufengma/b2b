package onefengma.demo.server.services.user;

import onefengma.demo.server.core.UpdateBuilder;
import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.admin.AdminSellersResponse;
import onefengma.demo.server.model.admin.AdminUsersResponse;
import onefengma.demo.server.model.apibeans.admin.AdminBuysResponse;
import onefengma.demo.server.model.apibeans.admin.AdminHandingVerifyResponse;
import onefengma.demo.server.model.apibeans.admin.AdminHelpFindProductResponse;
import onefengma.demo.server.model.apibeans.admin.AdminIronVerifyResponse;
import onefengma.demo.server.model.apibeans.admin.AdminOrdersResponse;
import onefengma.demo.server.model.apibeans.admin.AdminSalessResponse;
import onefengma.demo.server.model.apibeans.admin.AdminSellerVerifyResponse;
import onefengma.demo.server.model.apibeans.others.HelpFindProduct;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper.HandingBuyOfferDetail;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.products.IronDataHelper.IronBuyOfferDetail;


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
        String sellerSql = "delete from seller where userId=:userId";
        String bakSql = "insert into user_bak select * from user where userId=:userId";

        try (Connection conn = getConn()) {
            conn.createQuery(bakSql).addParameter("userId", userId).executeUpdate();
            conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
            conn.createQuery(sellerSql).addParameter("userId", userId).executeUpdate();
        }
    }

    public void deleteSeller(String sellerId) throws Exception {
        String sellerSql = "delete from seller where userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sellerSql).addParameter("userId", sellerId).executeUpdate();
        }
    }

    public void updateUser(String userId, float integral, int salesmanId) {
        String sql = "update user set salesManId=:salesManId,integral=:integral where userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("integral", integral)
                    .addParameter("userId", userId)
                    .addParameter("salesManId", salesmanId).executeUpdate();
        }
    }

    public void updateSeller(String sellerId, float integral, int salesmanId) {
        String sql = "update seller set integral=:integral where userId=:userId";
        String userSql = "update user set salesmanId=:salesManId where userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("integral", integral)
                    .addParameter("userId", sellerId).executeUpdate();

            conn.createQuery(userSql)
                    .addParameter("salesManId", salesmanId)
                    .addParameter("userId", sellerId).executeUpdate();
        }
    }

    public AdminUsersResponse getBuyer(PageBuilder pageBuilder) {
        String whereSql = pageBuilder.generateWhere();
        String maxCountSql = "select count(*) from user,salesman where salesman.id=buySellerId " + ((StringUtils.isEmpty(whereSql)) ? "" : " and " + whereSql);
        String userSalesMoneySql = "select userId,integral, mobile,registerTime, sum(ironMoney + handingMoney)  as buyMoney , tel as salesTel, salesmanId as salesId " +
                "from (select userId,integral, mobile,registerTime,salesmanId,tel from user,salesman where salesmanId=salesman.id) as userComplete" +
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
        String sellerSql = "select userId,integral,companyName,passTime as becomeSellerTime,contact as contactName,productCount,score, mobile,registerTime, sum(ironMoney + handingMoney)  as " + totalMoneyKeyName + " , tel as salesMobile,salesId " +
                "from (select companyName,seller.integral as integral,user.userId,passTime,contact, productCount,score,mobile,registerTime,user.salesManId as salesId, tel " +
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
                orderForAdmin.productType = productType;
                orderForAdmin.productId = row.getString("productId");
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

                Seller buyer = SellerDataHelper.instance().getSeller(buyerId);
                if (buyer != null) {
                    orderForAdmin.buyerCompany = buyer.companyName;
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

    public AdminBuysResponse getBuysForAdmin(PageBuilder pageBuilder, int productType) {
        String tableName = "";
        if (productType == 0) {
            tableName = "iron_buy";
        } else {
            tableName = "handing_buy";
        }
        String sql = "select * from " + tableName + " " + (pageBuilder.hasWhere() ? " where " : " ") + pageBuilder.generateWhere() + " " + pageBuilder.generateLimit();
        String countSql = "select count(*) from  " + tableName + " " + (pageBuilder.hasWhere() ? " where " : " ") + pageBuilder.generateWhere();

        AdminBuysResponse adminBuysResponse = new AdminBuysResponse();
        adminBuysResponse.currentPage = pageBuilder.currentPage;
        adminBuysResponse.pageCount = pageBuilder.pageCount;
        List<BuyForAdmin> buyForAdmins = new ArrayList<>();
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for (Row row : rows) {
                BuyForAdmin buyForAdmin = new BuyForAdmin();
                buyForAdmin.buyId = row.getString("id");
                buyForAdmin.count = productType == 0 ? row.getFloat("numbers") : 1;
                buyForAdmin.productType = productType;

                String supplyUserId = row.getString("supplyUserId");
                String buyerId = row.getString("userId");

                if (productType == 0) {
                    IronBuyOfferDetail detail = IronDataHelper.getIronDataHelper().getWinSellerOffer(buyForAdmin.buyId, supplyUserId);
                    if (detail != null) {
                        buyForAdmin.supplyPrice = detail.supplyPrice;
                        buyForAdmin.totalMoney = buyForAdmin.supplyPrice * buyForAdmin.count;
                    }
                } else {
                    HandingBuyOfferDetail detail = HandingDataHelper.getHandingDataHelper().getWinSellerOffer(buyForAdmin.buyId, supplyUserId);
                    if (detail != null) {
                        buyForAdmin.supplyPrice = detail.supplyPrice;
                        buyForAdmin.totalMoney = buyForAdmin.supplyPrice * buyForAdmin.count;
                    }
                }
                buyForAdmin.pushTime = row.getLong("pushTime");
                buyForAdmin.outDateTime = buyForAdmin.pushTime + row.getLong("timeLimit");
                buyForAdmin.finishTime = row.getLong("supplyWinTime");
                buyForAdmin.status = row.getInteger("status");

                SalesMan salesMan = UserDataHelper.instance().getSalesMan(buyerId);
                if (salesMan != null) {
                    buyForAdmin.salesManId = salesMan.id;
                    buyForAdmin.salesManMobile = salesMan.tel;
                }

                buyForAdmin.buyerMobile = UserDataHelper.instance().getUserMobile(buyerId);

                Seller seller = SellerDataHelper.instance().getSeller(supplyUserId);
                if (seller != null) {
                    buyForAdmin.sellerMobile = UserDataHelper.instance().getUserMobile(supplyUserId);
                    buyForAdmin.sellerCompany = seller.companyName;
                }

                buyForAdmins.add(buyForAdmin);
            }

            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;

            adminBuysResponse.maxCount = count;
            adminBuysResponse.buys = buyForAdmins;
        }
        return adminBuysResponse;
    }

    public AdminSalessResponse getSales(PageBuilder pageBuilder, long startTime, long endTime) {
        String salesSql = "select * from salesman where id<>0 " + (pageBuilder.hasWhere() ? " and " : " ") + pageBuilder.generateWhere() + " " + pageBuilder.generateLimit();
        String maxCountSql = "select count(*) from salesman where id<>0 " + (pageBuilder.hasWhere() ? " and " : " ") + pageBuilder.generateWhere() + " ";
        String userCountSql = "select userId from user where salesManId=:id and salesBindTime<:endTime and salesBindTime>=:startTime  ";

        String orderMoneySql = "select sum(totalMoney) from product_orders where buyerId=:buyerId and status=1 and finishTime<:endTime and finishTime>=:startTime";
        String handingBuyMoneySql = "select sum(supplyPrice) from handing_buy,handing_buy_supply " +
                "where handing_buy.supplyUserId = handing_buy_supply.sellerId and handing_buy_supply.sellerId=:userId" +
                " and supplyWinTime<:endTime and supplyWinTime>=:startTime ";
        String ironBuyMoneySql = "select sum(supplyPrice*numbers) from iron_buy,iron_buy_supply " +
                "where iron_buy.supplyUserId = iron_buy_supply.sellerId and iron_buy_supply.sellerId=:userId" +
                " and supplyWinTime<:endTime and supplyWinTime>=:startTime ";

        AdminSalessResponse adminSalessResponse = new AdminSalessResponse();
        adminSalessResponse.pageCount = pageBuilder.pageCount;
        adminSalessResponse.currentPage = pageBuilder.currentPage;

        List<SalesManAdmin> admins = new ArrayList<>();
        try(Connection conn = getConn()) {
            List<Row> rows = conn.createQuery(salesSql)
                            .executeAndFetchTable().rows();
            for(Row row : rows) {
                SalesManAdmin salesManAdmin = new SalesManAdmin();
                salesManAdmin.id = row.getInteger("id");
                salesManAdmin.name = row.getString("name");
                salesManAdmin.mobile = row.getString("tel");
                List<Row> userRows = conn.createQuery(userCountSql)
                        .addParameter("endTime", endTime)
                        .addParameter("startTime", startTime)
                        .addParameter("id", salesManAdmin.id).executeAndFetchTable().rows();
                salesManAdmin.userCount = userRows.size();
                for(Row userRow : userRows) {
                    String userId = userRow.getString("userId");
                    Float orderTotal = conn.createQuery(orderMoneySql)
                            .addParameter("startTime", startTime)
                            .addParameter("endTime", endTime)
                            .addParameter("buyerId", userId).executeScalar(Float.class);
                    orderTotal = orderTotal == null ? 0 : orderTotal;

                    Float handingBuyTotal = conn.createQuery(handingBuyMoneySql)
                            .addParameter("startTime", startTime)
                            .addParameter("endTime", endTime)
                            .addParameter("userId", userId).executeScalar(Float.class);
                    handingBuyTotal = handingBuyTotal == null ? 0 : handingBuyTotal;

                    Float ironBuyTotal = conn.createQuery(ironBuyMoneySql)
                            .addParameter("startTime", startTime)
                            .addParameter("endTime", endTime)
                            .addParameter("userId", userId).executeScalar(Float.class);
                    ironBuyTotal = ironBuyTotal == null ? 0 : ironBuyTotal;

                    salesManAdmin.totalMoney += handingBuyTotal + ironBuyTotal + orderTotal;
                }
                admins.add(salesManAdmin);
            }
            Integer maxCount = conn.createQuery(maxCountSql).executeScalar(Integer.class);
            maxCount = maxCount == null ? 0 : maxCount;
            adminSalessResponse.maxCount = maxCount;
        }
        adminSalessResponse.sales = admins;
        return adminSalessResponse;
    }

    public AdminSellerVerifyResponse getSellerVerify(PageBuilder pageBuilder) {
        String sql = "select * from seller where passed=false " + pageBuilder.generateLimit();
        String countSql = "select count(*) from seller where passed=false ";
        AdminSellerVerifyResponse adminSellerVerifyResponse = new AdminSellerVerifyResponse();
        adminSellerVerifyResponse.currentPage = pageBuilder.currentPage;
        adminSellerVerifyResponse.pageCount = pageBuilder.pageCount;
        try(Connection conn = getConn()) {
            List<SellerVerify> sellerVerifies = new ArrayList<>();
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for(Row row : rows) {
                SellerVerify sellerVerify = new SellerVerify();
                sellerVerify.userId = row.getString("userId");
                sellerVerify.companyName = row.getString("companyName");
                sellerVerify.mobile = row.getString("cantactTel");
                sellerVerify.applyTime = row.getLong("applyTime");

                SalesMan salesMan = UserDataHelper.instance().getSalesMan(sellerVerify.userId);
                if (salesMan != null) {
                    sellerVerify.salesManName = salesMan.name;
                    sellerVerify.salesManMobile = salesMan.tel;
                }
                sellerVerifies.add(sellerVerify);
            }

            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;

            adminSellerVerifyResponse.maxCount = count;
            adminSellerVerifyResponse.sellers = sellerVerifies;
        }
        return adminSellerVerifyResponse;
    }

    public void sellerVerifyOperation(String userId, boolean pass, String message) {
        if (pass) {
            doSellerVerify(userId, 1, message);
            InnerMessageDataHelper.instance().addInnerMessage(userId, "恭喜您申请成为商家成功！", "恭喜您成功入住淘不锈");
        } else {
            doSellerVerify(userId, 2, message);
            InnerMessageDataHelper.instance().addInnerMessage(userId, "很抱歉，申请成为商家失败！", "很抱歉，申请成为商家失败！");
        }
    }

    private void doSellerVerify(String userId, int pass, String message) {
        String sql = "update seller set passed=:pass , refuseMessage=:message where userId=:userId and passed=false ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId)
                    .addParameter("message", message)
                    .addParameter("pass", pass).executeUpdate();
        }
    }

    public boolean isSellerApplyHandled(String userId) {
        String sql = "select count(*) from seller where userId=:userId and passed=false ";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null || count == 0;
        }
    }

    public AdminIronVerifyResponse getIronVerify(PageBuilder pageBuilder) {
        String sql = "select * from iron_product where reviewed=false " + pageBuilder.generateLimit();
        String countSql = "select count(*) from iron_product where reviewed=false ";
        AdminIronVerifyResponse adminIronVerifyResponse = new AdminIronVerifyResponse();
        adminIronVerifyResponse.currentPage = pageBuilder.currentPage;
        adminIronVerifyResponse.pageCount = pageBuilder.pageCount;
        try(Connection conn = getConn()) {
            List<ProductVerify> productVerifies = new ArrayList<>();
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for(Row row : rows) {
                ProductVerify sellerVerify = new ProductVerify();
                sellerVerify.productId = row.getString("proId");
                sellerVerify.applyTime = row.getLong("pushTime");

                String userId = row.getString("userId");
                Seller seller = SellerDataHelper.instance().getSeller(userId);

                if (seller != null) {
                    sellerVerify.companyName = seller.companyName;
                    sellerVerify.mobile = seller.cantactTel;
                }

                SalesMan salesMan = UserDataHelper.instance().getSalesMan(userId);
                if (salesMan != null) {
                    sellerVerify.salesManName = salesMan.name;
                    sellerVerify.salesManMobile = salesMan.tel;
                }
                productVerifies.add(sellerVerify);
            }

            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;

            adminIronVerifyResponse.maxCount = count;
            adminIronVerifyResponse.products = productVerifies;
        }
        return adminIronVerifyResponse;
    }

    public void ironVerifyOperation(String proId, boolean pass, String message) throws Exception {

        if (pass) {
            doIronVerify(proId, 1, message);
        } else {
            doIronVerify(proId, 2, message);
        }
    }

    private void doIronVerify(String proId, int pass, String message) throws Exception {
        String sql = "update iron_product set reviewed=:pass , refuseMessage=:message where proId=:proId and reviewed=false ";
        transaction((conn)->{
            conn.createQuery(sql).addParameter("proId", proId)
                    .addParameter("message", message)
                    .addParameter("pass", pass).executeUpdate();

           SellerDataHelper.instance().updateSellerProductCount(conn, 0, proId);
        });
    }

    public AdminHandingVerifyResponse getHandingVerify(PageBuilder pageBuilder) {
        String sql = "select * from handing_product where reviewed=false " + pageBuilder.generateLimit();
        String countSql = "select count(*) from handing_product where reviewed=false ";
        AdminHandingVerifyResponse adminHandingVerifyResponse = new AdminHandingVerifyResponse();
        adminHandingVerifyResponse.currentPage = pageBuilder.currentPage;
        adminHandingVerifyResponse.pageCount = pageBuilder.pageCount;
        try(Connection conn = getConn()) {
            List<ProductVerify> productVerifies = new ArrayList<>();
            List<Row> rows = conn.createQuery(sql).executeAndFetchTable().rows();
            for(Row row : rows) {
                ProductVerify sellerVerify = new ProductVerify();
                sellerVerify.productId = row.getString("id");
                sellerVerify.applyTime = row.getLong("pushTime");

                String userId = row.getString("userId");
                Seller seller = SellerDataHelper.instance().getSeller(userId);

                if (seller != null) {
                    sellerVerify.companyName = seller.companyName;
                    sellerVerify.mobile = seller.cantactTel;
                }

                SalesMan salesMan = UserDataHelper.instance().getSalesMan(userId);
                if (salesMan != null) {
                    sellerVerify.salesManName = salesMan.name;
                    sellerVerify.salesManMobile = salesMan.tel;
                }
                productVerifies.add(sellerVerify);
            }

            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;

            adminHandingVerifyResponse.maxCount = count;
            adminHandingVerifyResponse.products = productVerifies;
        }
        return adminHandingVerifyResponse;
    }

    public AdminHelpFindProductResponse getHelpFindProducts(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(HelpFindProduct.class) + " from help_find_product " + pageBuilder.generateLimit();
        String countSql = "select count(*) from help_find_product";
        AdminHelpFindProductResponse response = new AdminHelpFindProductResponse();
        response.currentPage = pageBuilder.currentPage;
        response.pageCount = pageBuilder.pageCount;
        try(Connection conn = getConn()) {
            response.requests = conn.createQuery(sql).executeAndFetch(HelpFindProduct.class);
            Integer count = conn.createQuery(countSql).executeScalar(Integer.class);
            count = count == null ? 0 : count;
            response.maxCount = count;
        }
        return response;
    }

    public void deleteFindProduct(int id) {
        String sql = "delete from help_find_product where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public void handingVerifyOperation(String proId, boolean pass, String message) throws NoSuchFieldException, IllegalAccessException {

        if (pass) {
            doHandingVerify(proId, 1, message);
        } else {
            doHandingVerify(proId, 2, message);
        }
    }

    private void doHandingVerify(String proId, int pass, String message) throws NoSuchFieldException, IllegalAccessException {
        String sql = "update handing_product set reviewed=:pass , refuseMessage=:message where id=:proId and reviewed=false ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("proId", proId)
                    .addParameter("message", message)
                    .addParameter("pass", pass).executeUpdate();

            SellerDataHelper.instance().updateSellerProductCount(conn, 1, proId);
        }
    }

    public void updateSalesman(int id, String name, String mobile) {
        UpdateBuilder updateBuilder = new UpdateBuilder();
        updateBuilder.addStringMap("name", name);
        updateBuilder.addStringMap("tel", mobile);
        String sql = "update salesman set " + updateBuilder.generateSql() + " where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public void addNewSalesMan(String name, String mobile) {
        String sql = "insert into salesman set name=:name, tel=:tel";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("tel", mobile)
                    .executeUpdate();
        }
    }

    public static class ProductVerify {
        public String productId;
        public String productType;
        public String mobile;
        public long applyTime;
        public String companyName;
        public String salesManName;
        public String salesManMobile;
    }

    public static class SellerVerify {
        public String userId;
        public String mobile;
        public long applyTime;
        public String companyName;
        public String salesManName;
        public String salesManMobile;
    }

    public static class SalesManAdmin {
        public int id;
        public String name;
        public String mobile;
        public int userCount;
        public float totalMoney;
    }

    public static class BuyForAdmin {
        public String buyId;   // ok
        public int productType;  // ok
        public String buyerMobile;
        public String sellerMobile;
        public String sellerCompany;
        public float supplyPrice;  // ok
        public float count;    // ok
        public float totalMoney; // ok
        public long pushTime;  // ok
        public long outDateTime; // ok
        public long finishTime; // ok
        public int status;   // ok
        public int salesManId;   // ok
        public String salesManMobile; // ok
    }

    public static class OrderForAdmin {
        public String orderId;
        public String buyerMobile;
        public String buyerCompany;
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

        public String productId;
        public int productType;
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
        public float integral;

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
        public float integral;

        public long getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(long registerTime) {
            this.registerTime = registerTime;
        }
    }

}
