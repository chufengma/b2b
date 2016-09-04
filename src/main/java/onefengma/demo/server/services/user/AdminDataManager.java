package onefengma.demo.server.services.user;

import onefengma.demo.server.model.admin.AdminQtResponse;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.qt.QtBrief;
import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.NumberUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.UpdateBuilder;
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
import onefengma.demo.server.services.funcs.CityDataHelper;
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
            if (SellerDataHelper.instance().isSeller(userId)) {
                conn.createQuery(sellerSql).addParameter("userId", userId).executeUpdate();
            }
        }
    }

    public void deleteSeller(String sellerId) throws Exception {
        String sellerSql = "delete from seller where userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sellerSql).addParameter("userId", sellerId).executeUpdate();
        }
    }

    public void updateUser(String userId, BigDecimal integral, int salesmanId) {
        String sql = "update user set salesManId=:salesManId,integral=:integral where userId=:userId";
        try (Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("integral", integral.toPlainString())
                    .addParameter("userId", userId)
                    .addParameter("salesManId", salesmanId).executeUpdate();
        }
    }

    public void updateSeller(String sellerId, BigDecimal integral, int salesmanId) {
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
        String maxCountSql = "select count(*) from user left join salesman on salesman.id=salesManId " + ((StringUtils.isEmpty(whereSql)) ? "" : " where " + whereSql);
        String userSalesMoneySql = "select userId,integral, mobile,registerTime, sum(money)  as buyMoney , tel as salesTel, salesmanId as salesId " +
                "from (select userId,integral, mobile,registerTime,salesmanId,tel from user left join salesman on salesmanId=salesman.id) as userComplete " +
                " left join seller_transactions on (userId=buyerId and finishTime <=:endTime and finishTime>:startTime ) "
                + (StringUtils.isEmpty(pageBuilder.generateWhere()) ? "" : " where " + whereSql)
                + " group by userId  order by buyMoney desc" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            AdminUsersResponse usersResponse = new AdminUsersResponse();
            usersResponse.buyers = conn.createQuery(userSalesMoneySql)
                    .addParameter("startTime", pageBuilder.startTime)
                    .addParameter("endTime", pageBuilder.endTime)
                    .executeAndFetch(BuyerBrief.class);
            if (usersResponse.buyers != null) {
                for(BuyerBrief buyerBrief : usersResponse.buyers) {
                    buyerBrief.buyMoney = NumberUtils.round(buyerBrief.buyMoney, 2);
                    buyerBrief.integral = NumberUtils.round(buyerBrief.integral, 2);
                }
            }

            Long maxCount = conn.createQuery(maxCountSql).executeScalar(Long.class);
            usersResponse.maxCount = (maxCount == null ? 0 : maxCount);
            return usersResponse;
        }
    }

    public AdminSellersResponse getSellers(PageBuilder pageBuilder, long dataStartTime, long dateEndTime, boolean timeByBuy, String companyName) {
        String whereSql = pageBuilder.generateWhere();

        String companySql = StringUtils.isEmpty(companyName) ? "" :  "and companyName like '%" + companyName +"%' ";

        String maxCountSql = "select count(*) from user,seller,salesman where user.userId=seller.userId and user.salesManId=salesman.id "
                + " and registerTime<:registerEndTime and registerTime>=:registerStartTime " + companySql
                + ((StringUtils.isEmpty(whereSql)) ? "" : " and " + whereSql);

        String sellerSql = "select userId,integral,companyName, " +
                " passTime as becomeSellerTime, " +
                " contact as contactName,productCount,score, mobile,registerTime,  " +
                " sum(money)  as  sellerTotalMoney , " +
                " tel as salesMobile,salesId  " +
                " from (select * from (select  companyName, " +
                "        seller.integral as integral, user.userId, passTime,contact, productCount,score,mobile,registerTime, " +
                "        user.salesManId as salesId " +
                "        from seller,user where user.userId=seller.userId) as userTmp left join salesman on salesman.id = userTmp.salesId) " +
                " as userComplete " +
                " left join seller_transactions " +
                " on (userId=sellerId and finishTime>=:dataStartTime and finishTime<:dataEndTime) " +
                " where registerTime<:registerEndTime and registerTime>=:registerStartTime " + companySql +
                (StringUtils.isEmpty(pageBuilder.generateWhere()) ? "" : " and " + whereSql) +
                " group by userId " +
                " order by sellerTotalMoney desc " + pageBuilder.generateLimit();

        String buyerSql = "select sum(money) from seller_transactions where buyerId=:buyerId and finishTime>=:dataStartTime and finishTime<:dataEndTime ";

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

            if (adminSellersResponse.sellers != null) {
                for(SellerBrief sellerBrief : adminSellersResponse.sellers) {
                    Float buyerMoney = conn.createQuery(buyerSql).addParameter("buyerId", sellerBrief.userId)
                            .addParameter("dataStartTime", dataStartTime)
                            .addParameter("dataEndTime", dateEndTime).executeScalar(Float.class);
                    sellerBrief.buyerTotalMoney = buyerMoney == null ? 0 : buyerMoney;
                }
            }

            Integer maxCount = conn.createQuery(maxCountSql)
                    .addParameter("registerStartTime", pageBuilder.startTime)
                    .addParameter("registerEndTime", pageBuilder.endTime)
                    .executeScalar(Integer.class);

            adminSellersResponse.maxCount = maxCount == null ? 0 : maxCount;
        }
        return adminSellersResponse;
    }


    public AdminOrdersResponse getOrdersForAdmin(PageBuilder pageBuilder) {
        String sql = "select * from product_orders where status<>4  " + (pageBuilder.hasWhere() ? " and " : " ") + pageBuilder.generateWhere() + " order by sellTime desc " + pageBuilder.generateLimit();
        String countSql = "select count(*) from product_orders where status<>4   " + (pageBuilder.hasWhere() ? " and " : " ") + pageBuilder.generateWhere();

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
                    orderForAdmin.price = IronDataHelper.getIronDataHelper().getIronPrice(orderForAdmin.productId);
                } else {
                    orderForAdmin.price = HandingDataHelper.getHandingDataHelper().getHandingPrice(orderForAdmin.productId);
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

    public AdminBuysResponse getBuysForAdmin(PageBuilder pageBuilder, int productType) throws NoSuchFieldException, IllegalAccessException {
        String tableName = "";
        if (productType == 0) {
            tableName = "iron_buy";
        } else {
            tableName = "handing_buy";
        }
        String sql = "select * from " + tableName + " " + (pageBuilder.hasWhere() ? " where " : " ") + pageBuilder.generateWhere() + "  order by pushTime desc " + pageBuilder.generateLimit();
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
                    buyForAdmin.desc = row.getString("ironType") + " " + row.getString("surface")  + " " + row.getString("material")
                            + " " + row.getString("height") + "*" + row.getString("width") + "*" + row.getString("length") + " 公差：" + row.getString("tolerance") + " "
                            + row.getString("numbers") + "" + row.getString("unit") + " "
                            + "收货城市：" + CityDataHelper.instance().getCityDescById(row.getString("locationCityId")) + " " + row.getString("message");

                } else {
                    HandingBuyOfferDetail detail = HandingDataHelper.getHandingDataHelper().getWinSellerOffer(buyForAdmin.buyId, supplyUserId);
                    if (detail != null) {
                        buyForAdmin.supplyPrice = detail.supplyPrice;
                        buyForAdmin.totalMoney = buyForAdmin.supplyPrice * buyForAdmin.count;
                    }

                    buyForAdmin.desc = row.getString("handingType") + " " + row.getString("message") + " " + CityDataHelper.instance().getCityDescById(row.getString("souCityId"));
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
        String userCountSql = "select count(*) from user where salesManId=:id and salesBindTime<:endTime and salesBindTime>=:startTime  ";
        String userSql = "select userId from user where salesManId=:id ";

        String orderMoneySql = "select sum(money) as money from seller_transactions " +
                "where buyerId=:buyerId and finishTime<:endTime and finishTime>=:startTime and productType in (0, 2, 3)";

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
                Integer userNum = conn.createQuery(userCountSql)
                        .addParameter("endTime", endTime)
                        .addParameter("startTime", startTime)
                        .addParameter("id", salesManAdmin.id).executeScalar(Integer.class);

                salesManAdmin.userCount = userNum == null ? 0 : userNum;

                List<Row> userRows = conn.createQuery(userSql)
                        .addParameter("id", salesManAdmin.id).executeAndFetchTable().rows();

                for(Row userRow : userRows) {
                    String userId = userRow.getString("userId");
                    Float orderTotal = conn.createQuery(orderMoneySql)
                            .addParameter("startTime", startTime)
                            .addParameter("endTime", endTime)
                            .addParameter("buyerId", userId).executeScalar(Float.class);
                    orderTotal = orderTotal == null ? 0 : orderTotal;
                    salesManAdmin.totalMoney += NumberUtils.round(orderTotal, 2);
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
        String sql = "select * from seller where passed=false order by applyTime desc " + pageBuilder.generateLimit();
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
            InnerMessageDataHelper.instance().addInnerMessage(userId, "恭喜您成为入驻商户！", "恭喜您成为淘不锈商家用户，淘不锈为您提供智能求购匹配功能，请时刻保持登陆状态，我们能够帮您更快更准的找到潜在生意");
        } else {
            doSellerVerify(userId, 2, message);
            InnerMessageDataHelper.instance().addInnerMessage(userId, "很抱歉，申请成为商家失败！", "很抱歉，申请成为商家审核失败, 失败原因:" + message);
        }
    }

    private void doSellerVerify(String userId, int pass, String message) {
        String sql = "update seller set passed=:pass , refuseMessage=:message where userId=:userId and passed=false ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId)
                    .addParameter("message", message)
                    .addParameter("pass", pass).executeUpdate();
            if (pass == 2) {
                String deleteSeller = "delete from seller where userId=:userId and passed=2 ";
                conn.createQuery(deleteSeller).addParameter("userId", userId).executeUpdate();
            }
        }
    }

    public boolean isSellerApplyHandled(String userId) {
        String sql = "select count(*) from seller where userId=:userId and passed=false ";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null || count == 0;
        }
    }

    public Seller getAdminSeller(String sellerId) {
        String sql = "select " + generateFiledString(Seller.class) + " from seller where userId=:userId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("userId", sellerId).executeAndFetchFirst(Seller.class);
        }
    }

    public AdminIronVerifyResponse getIronVerify(PageBuilder pageBuilder) {
        String sql = "select * from iron_product where reviewed=false order by pushTime desc " + pageBuilder.generateLimit();
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
            if (pass == 1) {
                SellerDataHelper.instance().updateSellerProductCount(conn, 0, proId);
            }
        });
    }

    public AdminHandingVerifyResponse getHandingVerify(PageBuilder pageBuilder) {
        String sql = "select * from handing_product where reviewed=false order by pushTime desc " + pageBuilder.generateLimit();
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

    public AdminHelpFindProductResponse getHelpFindProducts(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
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
            if (response.requests != null) {
                for(HelpFindProduct helpFindProduct : response.requests) {
                    try {
                        helpFindProduct.city = CityDataHelper.instance().getCityDescById(helpFindProduct.city);
                    } catch (Exception e) {

                    }
                }
            }
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
            if (pass == 1) {
                SellerDataHelper.instance().updateSellerProductCount(conn, 1, proId);
            }
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

    public SiteInfo getSiteInfoAllTrans(long startTime, long endTime) {
        SiteInfo orderSiteInfo = getSiteInfoForOrderFromTrans(startTime, endTime);
        SiteInfo buySiteInfo = getSiteInfoForBuyFromTrans(startTime, endTime);
        orderSiteInfo.count = orderSiteInfo.count.add(buySiteInfo.count);
        orderSiteInfo.money = NumberUtils.round(orderSiteInfo.money.add(buySiteInfo.money), 2);
        return orderSiteInfo;
    }

    public SiteInfo getSiteInfoForOrder(long startTime, long endTime) {
        String ironSql = "select count(*) count, sum(count*price) as money " +
                         "from product_orders,iron_product " +
                         "where productType=0 and iron_product.proId=product_orders.productId and finishTime<:endTime and finishTime>=:startTime and status in (1 ,2)";

        String handingSql = "select count(*) count, sum(count*price) as money " +
                "from product_orders,handing_product " +
                "where productType=1 and handing_product.id=product_orders.productId and finishTime<:endTime and finishTime>=:startTime and status in (1 ,2) ";

        try(Connection conn = getConn()) {
            BigDecimal money = new BigDecimal(0);
            BigDecimal count = new BigDecimal(0);
            List<Row> ironRows = conn.createQuery(ironSql).addParameter("startTime", startTime)
                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
            if (ironRows.size() > 0) {
                Row row = ironRows.get(0);
                BigDecimal moneyTmp = row.getBigDecimal("money");
                moneyTmp = moneyTmp == null ? new BigDecimal(0) : moneyTmp;

                BigDecimal countTmp = row.getBigDecimal("count");
                countTmp = countTmp == null ? new BigDecimal(0) : countTmp;

                money = money.add(moneyTmp);
                count = count.add(countTmp);
            }

            List<Row> handingRows = conn.createQuery(handingSql).addParameter("startTime", startTime)
                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
            if (handingRows.size() > 0) {
                Row row = handingRows.get(0);
                BigDecimal moneyTmp = row.getBigDecimal("money");
                moneyTmp = moneyTmp == null ? new BigDecimal(0) : moneyTmp;

                BigDecimal countTmp = row.getBigDecimal("count");
                countTmp = countTmp == null ? new BigDecimal(0) : countTmp;

                money = money.add(moneyTmp);
                count = count.add(countTmp);
            }

            SiteInfo siteInfo = new SiteInfo();
            siteInfo.startTime = startTime;
            siteInfo.endTime = endTime;
            siteInfo.money = NumberUtils.round(money, 2);
            siteInfo.count = count;
            return siteInfo;
        }
    }

    public SiteInfo getSiteInfoForBuyFromTrans(long startTime, long endTime) {
        String sql = "select count(*) as count, sum(money) as money from seller_transactions where productType in (0) and finishTime<:endTime and finishTime>=:startTime ";

        try(Connection conn = getConn()) {
            BigDecimal money = new BigDecimal(0);
            BigDecimal count = new BigDecimal(0);
            List<Row> ironRows = conn.createQuery(sql).addParameter("startTime", startTime)
                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
            if (ironRows.size() > 0) {
                Row row = ironRows.get(0);
                count = row.getBigDecimal("count") == null ? new BigDecimal(0) : row.getBigDecimal("count");
                money = row.getBigDecimal("money") == null ? new BigDecimal(0) : row.getBigDecimal("money");
            }
            SiteInfo siteInfo = new SiteInfo();
            siteInfo.startTime = startTime;
            siteInfo.endTime = endTime;
            siteInfo.money = NumberUtils.round(money, 2);
            siteInfo.count = count;
            return siteInfo;
        }
    }


    public SiteInfo getSiteInfoForOrderFromTrans(long startTime, long endTime) {
        String sql = "select count(*) as count, sum(money) as money from seller_transactions where productType in (2, 3) and finishTime<:endTime and finishTime>=:startTime ";

        try(Connection conn = getConn()) {
            BigDecimal money = new BigDecimal(0);
            BigDecimal count = new BigDecimal(0);
            List<Row> ironRows = conn.createQuery(sql).addParameter("startTime", startTime)
                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
            if (ironRows.size() > 0) {
                Row row = ironRows.get(0);
                count = row.getBigDecimal("count") == null ? new BigDecimal(0) : row.getBigDecimal("count");
                money = row.getBigDecimal("money") == null ? new BigDecimal(0) : row.getBigDecimal("money");
            }
            SiteInfo siteInfo = new SiteInfo();
            siteInfo.startTime = startTime;
            siteInfo.endTime = endTime;
            siteInfo.money = NumberUtils.round(money, 2);
            siteInfo.count = count;
            return siteInfo;
        }
    }

    public SiteInfo getSiteInfoForBuy(long startTime, long endTime) {
        String ironSql = "select sum(supplyPrice*numbers) as money, count(iron_buy.id) as count " +
                        "from iron_buy left join iron_buy_supply " +
                        "on iron_buy_supply.sellerId=iron_buy.supplyUserId and iron_buy.id = iron_buy_supply.ironId " +
                        "where iron_buy.status=1 and supplyWinTime>=:startTime and supplyWinTime<:endTime";

//        String handingSql = "select sum(supplyPrice) as money, count(handing_buy.id) as count " +
//                "from handing_buy left join handing_buy_supply " +
//                "on handing_buy_supply.sellerId=handing_buy.supplyUserId  and supplyWinTime>=:startTime and supplyWinTime<:endTime and handing_buy_supply.handingId = handing_buy.id " +
//                "where handing_buy.status=1 ";

        try(Connection conn = getConn()) {
            BigDecimal money = new BigDecimal(0);
            BigDecimal count = new BigDecimal(0);
            List<Row> ironRows = conn.createQuery(ironSql).addParameter("startTime", startTime)
                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
            if (ironRows.size() > 0) {
                Row row = ironRows.get(0);
                count = row.getBigDecimal("count") == null ? new BigDecimal(0) : row.getBigDecimal("count");
                money = row.getBigDecimal("money") == null ? new BigDecimal(0) : row.getBigDecimal("money");
            }

//            List<Row> handingRows = conn.createQuery(handingSql).addParameter("startTime", startTime)
//                    .addParameter("endTime", endTime).executeAndFetchTable().rows();
//            if (handingRows.size() > 0) {
//                Row row = handingRows.get(0);
//                Float moneyTmp = row.getFloat("money");
//                moneyTmp = moneyTmp == null ? 0 : moneyTmp;
//
//                Float countTmp = row.getFloat("count");
//                countTmp = countTmp == null ? 0 : countTmp;
//
//                money += moneyTmp;
//                count += countTmp;
//            }

            SiteInfo siteInfo = new SiteInfo();
            siteInfo.startTime = startTime;
            siteInfo.endTime = endTime;
            siteInfo.money = NumberUtils.round(money, 2);
            siteInfo.count = count;
            return siteInfo;
        }
    }

    public AdminQtResponse getQtListResponse(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String where = StringUtils.isEmpty(pageBuilder.generateWherePlus(true)) ? " where " : pageBuilder.generateWherePlus(true) + " and ";

        String sql = "select " + generateFiledStringExclude(QtItem.class, "userMobile", "sellerMobile", "sellerCompany", "desc") + " from iron_buy_qt " + where
                + " pushTime<:endTime and pushTime>=:startTime order by pushTime desc " + pageBuilder.generateLimit() ;

        String countSql = "select count(*) from iron_buy_qt " + where
                + " pushTime<:endTime and pushTime>=:startTime order by pushTime desc " + pageBuilder.generateLimit() ;
        try(Connection conn = getConn()) {
            AdminQtResponse qtResponse = new AdminQtResponse();
            qtResponse.currentPage = pageBuilder.currentPage;
            qtResponse.pageCount = pageBuilder.pageCount;
            qtResponse.qtBriefList = conn.createQuery(sql).addParameter("startTime", pageBuilder.startTime)
                    .addParameter("endTime", pageBuilder.endTime).executeAndFetch(QtItem.class);
            Integer maxCount = conn.createQuery(countSql).addParameter("startTime", pageBuilder.startTime)
                    .addParameter("endTime", pageBuilder.endTime).executeScalar(Integer.class);
            maxCount = maxCount == null ? 0 : maxCount;
            qtResponse.maxCount = maxCount;

            for(QtItem qtItem : qtResponse.qtBriefList) {
                IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(qtItem.ironBuyId);
                qtItem.userMobile = UserDataHelper.instance().getUserMobile(ironBuyBrief.userId);
                Seller seller = SellerDataHelper.instance().getSeller(ironBuyBrief.supplyUserId);
                if (seller != null) {
                    qtItem.sellerCompany = seller.companyName;
                }
                qtItem.desc =  ironBuyBrief.ironType + " " + ironBuyBrief.surface  + " " + ironBuyBrief.material
                        + " " + ironBuyBrief.height + "*" + ironBuyBrief.width + "*" + ironBuyBrief.length + " 公差：" + ironBuyBrief.tolerance + " "
                        + ironBuyBrief.numbers + "" + ironBuyBrief.unit + " "
                        + "收货城市：" + CityDataHelper.instance().getCityDescById(ironBuyBrief.locationCityId) + " " + ironBuyBrief.message;

                qtItem.sellerMobile = UserDataHelper.instance().getUserMobile(ironBuyBrief.supplyUserId);
            }

            return qtResponse;
        }
    }
    public static class QtItem {
        public String qtId;
        public int salesmanId;
        public String ironBuyId;
        public int status; // 0等待质检 1质检完成 2质检取消
        public long pushTime;
        public long finishTime;
        public String userId;
        public String userMobile;
        public String sellerMobile;
        public String sellerCompany;
        public String desc;
    }

    public static class SiteInfo {
        public long startTime;
        public long endTime;
        public BigDecimal count;
        public BigDecimal money;
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

        public String desc;
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
        public BigDecimal score;
        public int salesId;
        public String salesMobile;
        public BigDecimal integral;

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
        public BigDecimal buyMoney;
        public String userId;
        public BigDecimal integral;

        public long getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(long registerTime) {
            this.registerTime = registerTime;
        }
    }

}
