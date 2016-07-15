package onefengma.demo.server.services.user;

import java.util.List;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.DateHelper.TimeRange;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.admin.AdminDetailRequest;
import onefengma.demo.server.model.admin.AdminOperationRequest;
import onefengma.demo.server.model.admin.AdminSellersRequest;
import onefengma.demo.server.model.admin.AdminSellersResponse;
import onefengma.demo.server.model.admin.AdminUsersRequest;
import onefengma.demo.server.model.admin.AdminUsersResponse;
import onefengma.demo.server.model.apibeans.admin.AdminBuysRequest;
import onefengma.demo.server.model.apibeans.admin.AdminFindHelpRequest;
import onefengma.demo.server.model.apibeans.admin.AdminLoginRequest;
import onefengma.demo.server.model.apibeans.admin.AdminOrdersRequest;
import onefengma.demo.server.model.apibeans.admin.AdminSalesRequest;
import onefengma.demo.server.model.apibeans.admin.DeleteUserRequest;
import onefengma.demo.server.model.apibeans.admin.UpdateUserRequest;
import onefengma.demo.server.model.apibeans.others.InnerMessageRequest;
import onefengma.demo.server.model.product.HandingDetail;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import spark.Session;

/**
 * Created by chufengma on 16/7/2.
 */
public class AdminManager extends BaseManager {

    @Override
    public void init() {
        post("login", AdminLoginRequest.class, ((request, response, requestBean) -> {
            Admin admin = AdminDataManager.instance().getAdmin(requestBean.userName);
            if (admin == null) {
                return error("没有该管理员");
            }
            if (StringUtils.equalsIngcase(IdUtils.md5(requestBean.password), admin.password)) {
                Session session = request.session();
                session.attribute("admin", admin.id + "");
                session.maxInactiveInterval(30 * 60);
                response.cookie("admin", admin.id + "", 30 * 60);
                return success("登陆成功");
            } else {
                return error("密码不正确");
            }
        }));

        get("users", AdminUsersRequest.class, ((request, response, requestBean) -> {
            if (requestBean.time == 0) {
                requestBean.time = System.currentTimeMillis();
            }
            TimeRange timeRange = DateHelper.getMonthTimeRange(requestBean.time);
            AdminUsersResponse adminUsersResponse = AdminDataManager.instance()
                    .getBuyer(new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                            .setTime(timeRange.startTime, timeRange.endTime)
                            .addEqualWhere("mobile", requestBean.userTel)
                            .addEqualWhere("salesTel", requestBean.salesTel));
            adminUsersResponse.currentPage = requestBean.currentPage;
            adminUsersResponse.pageCount = requestBean.pageCount;
            return success(adminUsersResponse);
        }));

        post("deleteUser", DeleteUserRequest.class, ((request, response, requestBean) -> {
            AdminDataManager.instance().deleteUser(requestBean.userId);
            return success("删除成功");
        }));

        post("updateUser", UpdateUserRequest.class, ((request, response, requestBean) -> {
            if (!UserDataHelper.instance().isSalesManExited(requestBean.salesmanId)) {
                return error("该顾问不存在");
            }
            AdminDataManager.instance().updateUser(requestBean.userId, requestBean.salesmanId);
            return success("修改成功");
        }));

        get("sellers", AdminSellersRequest.class, ((request, response, requestBean) -> {
            boolean isBuyerStart = false;
            long dateStartTime;
            long dateEndTime;
            if (requestBean.buyTimeEnd != -1 && requestBean.buyTimeStart != -1) {
                isBuyerStart = true;
                dateStartTime = requestBean.buyTimeStart;
                dateEndTime = requestBean.buyTimeEnd;
            } else {
                isBuyerStart = false;
                dateStartTime = requestBean.sellTimeStart;
                dateEndTime = requestBean.sellTimeEnd;
            }

            AdminSellersResponse adminSellersResponse = AdminDataManager.instance()
                    .getSellers(new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                            .addEqualWhere("mobile", requestBean.salesMobile)
                            .addEqualWhere("salesMobile", requestBean.salesMobile)
                            .setTime(requestBean.becomeSellerTimeStart, requestBean.becomeSellerTimeEnd)
                            , dateStartTime, dateEndTime, isBuyerStart, requestBean.companyName);

            return success(adminSellersResponse);
        }));

        get("orders", AdminOrdersRequest.class, ((request, response, requestBean) -> {

            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("status", requestBean.status)
                    .addEqualWhere("id", requestBean.orderId);

            if (!StringUtils.isEmpty(requestBean.buyerMobile)) {
                String buyerId = UserDataHelper.instance().getUserIdByMobile(requestBean.buyerMobile);
                buyerId = buyerId == null ? "" : buyerId;
                pageBuilder.addEqualWhereCanEmpty("buyerId", buyerId);
            }

            if (!StringUtils.isEmpty(requestBean.sellerMobile)) {
                String sellerId = UserDataHelper.instance().getUserIdByMobile(requestBean.sellerMobile);
                sellerId = sellerId == null ? "" : sellerId;
                pageBuilder.addEqualWhereCanEmpty("sellerId", sellerId);
            }

            if (!StringUtils.isEmpty(requestBean.salesManMobile)) {
                String salesManID = UserDataHelper.instance().getSalesManIdByMobile(requestBean.salesManMobile);
                salesManID = salesManID == null ? "" : salesManID;
                pageBuilder.addEqualWhereCanEmpty("salesmanId", salesManID);
            }

            if (!StringUtils.isEmpty(requestBean.buyerCompanyName)) {
                List<String> ids = SellerDataHelper.instance().getUserIdsByCompanyName(requestBean.buyerCompanyName);
                if (ids.isEmpty()) {
                    ids.add("");
                }
                pageBuilder.addInWhere("buyerId", ids);
            }

            if (!StringUtils.isEmpty(requestBean.sellerCompanyName)) {
                List<String> ids = SellerDataHelper.instance().getUserIdsByCompanyName(requestBean.sellerCompanyName);
                if (ids.isEmpty()) {
                    ids.add("");
                }
                pageBuilder.addInWhere("sellerId", ids);
            }

            return success(AdminDataManager.instance().getOrdersForAdmin(pageBuilder));
        }));


        get("buys", AdminBuysRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                    .addEqualWhere("status", requestBean.status)
                    .addEqualWhere("id", requestBean.buyId);


            if (!StringUtils.isEmpty(requestBean.buyerMobile)) {
                String buyerId = UserDataHelper.instance().getUserIdByMobile(requestBean.buyerMobile);
                buyerId = buyerId == null ? "" : buyerId;
                pageBuilder.addEqualWhereCanEmpty("userId", buyerId);
            }

            if (!StringUtils.isEmpty(requestBean.sellerMobile)) {
                String sellerId = UserDataHelper.instance().getUserIdByMobile(requestBean.sellerMobile);
                sellerId = sellerId == null ? "" : sellerId;
                pageBuilder.addEqualWhereCanEmpty("supplyUserId", sellerId);
            }

            if (!StringUtils.isEmpty(requestBean.buyerCompanyName)) {
                List<String> ids = SellerDataHelper.instance().getUserIdsByCompanyName(requestBean.buyerCompanyName);
                if (ids.isEmpty()) {
                    ids.add("");
                }
                pageBuilder.addInWhere("userId", ids);
            }

            if (!StringUtils.isEmpty(requestBean.salesManMobile)) {
                String id = UserDataHelper.instance().getSalesManIdByMobile(requestBean.salesManMobile);
                pageBuilder.addEqualWhere("salesmanId", id);
            }

            if (!StringUtils.isEmpty(requestBean.sellerCompanyName)) {
                List<String> ids = SellerDataHelper.instance().getUserIdsByCompanyName(requestBean.sellerCompanyName);
                if (ids.isEmpty()) {
                    ids.add("");
                }
                pageBuilder.addInWhere("supplyUserId", ids);
            }

            return success(AdminDataManager.instance().getBuysForAdmin(pageBuilder, requestBean.productType));
        }));

        get("salesmans", AdminSalesRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            pageBuilder.addEqualWhere("id", requestBean.salesManId)
            .addEqualWhere("tel", requestBean.salesManMobile);

            if (requestBean.startTime == -1 || requestBean.endTime == -1) {
                requestBean.startTime = DateHelper.getThisMonthStartTimestamp();
                requestBean.endTime = DateHelper.getNextMonthStatimestamp();
            }

            return success(AdminDataManager.instance().getSales(pageBuilder, requestBean.startTime, requestBean.endTime));
        }));

        get("sellerVerify", BaseAdminPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(AdminDataManager.instance().getSellerVerify(pageBuilder));
        }));

        get("sellerVerifyDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            if (AdminDataManager.instance().isSellerApplyHandled(requestBean.id)) {
                return error("用户不再审核范围之内");
            }
            return success(SellerDataHelper.instance().getSeller(requestBean.id));
        }));

        post("sellerVerifyOp", AdminOperationRequest.class, ((request, response, requestBean) -> {
            if (AdminDataManager.instance().isSellerApplyHandled(requestBean.id)) {
                return error("用户不再审核范围之内");
            }
            AdminDataManager.instance().sellerVerifyOperation(requestBean.id, requestBean.operation == 1, requestBean.message);
            return success("操作成功");
        }));

        get("ironVerify", BaseAdminPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(AdminDataManager.instance().getIronVerify(pageBuilder));
        }));

        get("ironVerifyDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            IronDetail ironDetail = IronDataHelper.getIronDataHelper().getIronProductById(requestBean.id);
            if (ironDetail == null) {
                return error("找不到该产品信息");
            }
            return success(ironDetail);
        }));

        post("ironVerifyOp", AdminOperationRequest.class, ((request, response, requestBean) -> {
            IronDetail ironDetail = IronDataHelper.getIronDataHelper().getIronProductById(requestBean.id);
            if (ironDetail == null) {
                return error("找不到该产品信息");
            }
            AdminDataManager.instance().ironVerifyOperation(requestBean.id, requestBean.operation == 1, requestBean.message);
            if (requestBean.operation == 1) {
                InnerMessageDataHelper.instance().addInnerMessage(ironDetail.userId, "恭喜资源成功", "恭喜资源成功");
            } else if (requestBean.operation == 2)  {
                InnerMessageDataHelper.instance().addInnerMessage(ironDetail.userId, "恭喜资源失败！", "很抱歉，恭喜资源失败！");
            }
            return success("操作成功");
        }));

        get("handingVerify", BaseAdminPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(AdminDataManager.instance().getHandingVerify(pageBuilder));
        }));

        get("handingVerifyDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            HandingDetail handingDetail = HandingDataHelper.getHandingDataHelper().getHandingProductById(requestBean.id);
            if (handingDetail == null) {
                return error("找不到该产品信息");
            }
            return success(handingDetail);
        }));

        post("handingVerifyOp", AdminOperationRequest.class, ((request, response, requestBean) -> {
            HandingDetail handingDetail = HandingDataHelper.getHandingDataHelper().getHandingProductById(requestBean.id);
            if (handingDetail == null) {
                return error("找不到该产品信息");
            }
            AdminDataManager.instance().handingVerifyOperation(requestBean.id, requestBean.operation == 1, requestBean.message);
            if (requestBean.operation == 1) {
                InnerMessageDataHelper.instance().addInnerMessage(handingDetail.userId, "恭喜资源成功", "恭喜资源成功");
            } else if (requestBean.operation == 2)  {
                InnerMessageDataHelper.instance().addInnerMessage(handingDetail.userId, "恭喜资源失败！", "很抱歉，恭喜资源失败！");
            }
            return success("操作成功");
        }));

        get("findHelpProduct", BaseAdminPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(AdminDataManager.instance().getHelpFindProducts(pageBuilder));
        }));

        post("deleteFindHelpProduct", AdminFindHelpRequest.class, ((request, response, requestBean) -> {
            AdminDataManager.instance().deleteFindProduct(requestBean.id);
            return success("操作成功");
        }));

        post("postInnerMessage", InnerMessageRequest.class, ((request, response, requestBean) -> {
            String userId = UserDataHelper.instance().getUserIdByMobile(requestBean.mobile);
            if (StringUtils.isEmpty(userId)) {
                return error("该用户不存在！");
            }
            InnerMessageDataHelper.instance().addInnerMessage(userId, requestBean.title, requestBean.message);
            return success("操作成功");
        }));
    }


    @Override
    public String getParentRoutePath() {
        return "admin";
    }
}
