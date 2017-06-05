package onefengma.demo.server.services.user;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.DateHelper.TimeRange;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.*;
import onefengma.demo.server.core.request.TypedRoute;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.User;
import onefengma.demo.server.model.UserProfile;
import onefengma.demo.server.model.admin.*;
import onefengma.demo.server.model.apibeans.admin.*;
import onefengma.demo.server.model.apibeans.logistics.LogisticsActionRequst;
import onefengma.demo.server.model.apibeans.logistics.LogisticsDeleteRequst;
import onefengma.demo.server.model.apibeans.logistics.LogisticsPageRequst;
import onefengma.demo.server.model.apibeans.order.SiteInfoRequest;
import onefengma.demo.server.model.apibeans.others.AddNewsRequest;
import onefengma.demo.server.model.apibeans.others.AddRecruitRequest;
import onefengma.demo.server.model.apibeans.others.AddSalesRequest;
import onefengma.demo.server.model.apibeans.others.EditNewsRequest;
import onefengma.demo.server.model.apibeans.others.EditRecruitRequest;
import onefengma.demo.server.model.apibeans.others.InnerMessageRequest;
import onefengma.demo.server.model.apibeans.others.NewsDetailRequest;
import onefengma.demo.server.model.apibeans.product.IronBuyRequest;
import onefengma.demo.server.model.logistics.LogisticsNormalBean;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.product.*;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;
import onefengma.demo.server.services.funcs.NewsDataHelper;
import onefengma.demo.server.services.funcs.RecruitDataManager;
import onefengma.demo.server.services.funcs.RecruitDataManager.RecruitDetail;
import onefengma.demo.server.services.logistics.LogisticsDataManager;
import onefengma.demo.server.services.order.OrderDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import spark.Session;

import static onefengma.demo.server.services.products.IronDataHelper.getIronDataHelper;

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
                session.attribute("role", admin.role + "");
                session.maxInactiveInterval(30 * 60);
                response.cookie("admin", admin.id + "", 30 * 60);
                response.cookie("role", admin.role + "");
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
                            .addEqualWhere("tel", requestBean.salesTel));
            adminUsersResponse.currentPage = requestBean.currentPage;
            adminUsersResponse.pageCount = requestBean.pageCount;
            return success(adminUsersResponse);
        }));

        post("deleteUser", DeleteUserRequest.class, ((request, response, requestBean) -> {
            AdminDataManager.instance().deleteUser(requestBean.userId);
            return success("删除成功");
        }));

        post("deleteSeller", DeleteUserRequest.class, ((request, response, requestBean) -> {
            AdminDataManager.instance().deleteSeller(requestBean.userId);
            return success("删除成功");
        }));

        post("updateUser", UpdateUserRequest.class, ((request, response, requestBean) -> {
            if (!UserDataHelper.instance().isSalesManExited(requestBean.salesmanId)) {
                return error("该顾问不存在");
            }
            LogUtils.i(" user update! salesmanId : " + requestBean.salesmanId +
                    ", integral " + requestBean.integral +
                    ", userId:" + requestBean.userId, true);
            AdminDataManager.instance().updateUser(requestBean.userId, requestBean.integral,requestBean.salesmanId);

            SalesMan salesMan = UserDataHelper.instance().getSalesMan(requestBean.userId);
            if (salesMan == null || salesMan.id != requestBean.salesmanId) {
                UserDataHelper.instance().updateSalesmanBindTime(requestBean.salesmanId, requestBean.userId);
            }

            return success("修改成功");
        }));

        post("updateSeller", UpdateUserRequest.class, ((request, response, requestBean) -> {
            if (!UserDataHelper.instance().isSalesManExited(requestBean.salesmanId)) {
                return error("该顾问不存在");
            }
            LogUtils.i(" seller update! salesmanId : " + requestBean.salesmanId +
                    ", integral " + requestBean.integral +
                    ", userId:" + requestBean.userId, true);
            AdminDataManager.instance().updateSeller(requestBean.userId, requestBean.integral, requestBean.salesmanId);

            SalesMan salesMan = UserDataHelper.instance().getSalesMan(requestBean.userId);
            if (salesMan == null || salesMan.id != requestBean.salesmanId) {
                UserDataHelper.instance().updateSalesmanBindTime(requestBean.salesmanId, requestBean.userId);
            }
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
                            .addEqualWhere("mobile", requestBean.userMobile)
                            .addEqualWhere("tel", requestBean.salesMobile)
                            .setTime(requestBean.becomeSellerTimeStart, requestBean.becomeSellerTimeEnd)
                            , dateStartTime, dateEndTime, isBuyerStart, requestBean.companyName, !StringUtils.isEmpty(requestBean.userMobile) || !StringUtils.isEmpty(requestBean.salesMobile));

            return success(adminSellersResponse);
        }));

        get("orders", AdminOrdersRequest.class, ((request, response, requestBean) -> {

            OrderDataHelper.instance().updateOrderStatus();

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
            OrderDataHelper.instance().updateOutofDateStatic("", "");
            return success(AdminDataManager.instance().getOrdersForAdmin(pageBuilder));
        }));

        post("deleteOrder", AdminDeleteOrderRequest.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderExited(requestBean.orderId)) {
                return error("该订单不存在");
            }
//            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
//            if (status == 0) {
//                return error("订单正在进行中,无法删除");
//            }
            OrderDataHelper.instance().deleteOrderBuyAdmin(requestBean.orderId);
            return success("删除成功");
        }));


        post("buyByAdmin", IronBuyRequestByAdmin.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.userId)) {
                return error("该用户不是企业用户，不能发布求购, 请前往后台点击成为商家上传公司三证等相关资料");
            }

            // 材料种类
            if (!IconDataCategory.get().materials.contains(requestBean.material)) {
                return errorAndClear(requestBean, "材料种类填写不正确");
            }
            // 表面种类
            if (!IconDataCategory.get().surfaces.contains(requestBean.surface)) {
                return errorAndClear(requestBean, "表面种类填写不正确");
            }
            // 不锈钢品类
            if (!IconDataCategory.get().types.contains(requestBean.ironType)) {
                return errorAndClear(requestBean, "不锈钢品类填写不正确");
            }
            // 不锈钢产地
            if (!IconDataCategory.get().productPlaces.contains(requestBean.proPlace)) {
                return errorAndClear(requestBean, "不锈钢产地填写不正确");
            }

            if (!CityDataHelper.instance().isCityExist(requestBean.locationCityId)) {
                return errorAndClear(requestBean, "交货地点不存在");
            }
            IronBuy ironBuy = requestBean.generateIronBuy();
            ironBuy.salesmanId = UserDataHelper.instance().getSalesManId(requestBean.userId);
            IronDataHelper.getIronDataHelper().pushIronBuy(ironBuy);

            return success();
        }));

        get("buys", AdminBuysRequest.class, ((request, response, requestBean) -> {
            getIronDataHelper().updateBuyStatus();
            HandingDataHelper.getHandingDataHelper().updateBuyStatus();

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

            if (requestBean.appFlag != -1) {
                pageBuilder.addInWhereNumber("appFlag", Arrays.asList(requestBean.appFlag));
            }

            return success(AdminDataManager.instance().getBuysForAdmin(pageBuilder, requestBean.productType));
        }));


        post("deleteBuy", AdminDeleteBuyRequest.class, ((request, response, requestBean) -> {
            LogUtils.i("admin delete buy data!  adminId:" + request.cookie("admin") + ", productType:" + requestBean.productType + ", productId=" + requestBean.proId, true);
            if (requestBean.productType == 0) {
                getIronDataHelper().deleteIronBuy(requestBean.proId);
            } else if (requestBean.productType == 1) {
                HandingDataHelper.getHandingDataHelper().deleteHandingBuy(requestBean.proId);
            }
            return success("删除订单成功");
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

        post("updateSalesman", AdminChangeSalesmanRequest.class, ((request, response, requestBean) -> {
            if (UserDataHelper.instance().getSalesManById(requestBean.id) == null) {
                return error("没有该业务员");
            }
            if (StringUtils.isEmpty(requestBean.name) || StringUtils.isEmpty(requestBean.mobile)) {
                return success();
            }

            if (!StringUtils.isEmpty(requestBean.mobile)) {
                if (!VerifyUtils.isMobile(requestBean.mobile)) {
                    return error("手机号格式不正确");
                }
            }

            String password = "";
            if (!StringUtils.isEmpty(requestBean.password) && !StringUtils.equals(StringUtils.DEFAULT_PASSWORD, requestBean.password)) {
                password = IdUtils.md5(requestBean.password);
            }
            AdminDataManager.instance().updateSalesman(requestBean.id, requestBean.name, requestBean.mobile, password);
            return success("操作成功");
        }));

        get("sellerVerify", BaseAdminPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            return success(AdminDataManager.instance().getSellerVerify(pageBuilder));
        }));

        get("sellerVerifyDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            if (AdminDataManager.instance().isSellerApplyHandled(requestBean.id)) {
                return error("用户不再审核范围之内");
            }
            return success(AdminDataManager.instance().getAdminSeller(requestBean.id));
        }));

        get("sellerInfoDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            return success(AdminDataManager.instance().getAdminSeller(requestBean.id));
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
            IronDetail ironDetail = getIronDataHelper().getIronProductById(requestBean.id);
            if (ironDetail == null) {
                return error("找不到该产品信息");
            }
            ironDetail.setCityName(CityDataHelper.instance().getCityDescById(ironDetail.sourceCityId));
            return success(ironDetail);
        }));

        post("ironVerifyOp", AdminOperationRequest.class, ((request, response, requestBean) -> {
            IronDetail ironDetail = getIronDataHelper().getIronProductById(requestBean.id);
            if (ironDetail == null) {
                return error("找不到该产品信息");
            }
            AdminDataManager.instance().ironVerifyOperation(requestBean.id, requestBean.operation == 1, requestBean.message);
            if (requestBean.operation == 1) {
                InnerMessageDataHelper.instance().addInnerMessage(ironDetail.userId, "恭喜资源发布成功", "恭喜您发布的不锈钢产品审核通过");
            } else if (requestBean.operation == 2)  {
                InnerMessageDataHelper.instance().addInnerMessage(ironDetail.userId, "资源发布失败！", "很抱歉您的不锈钢产品发布失败！");
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
            handingDetail.setCityName(CityDataHelper.instance().getCityDescById(handingDetail.souCityId));
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

        post("addSales", AddSalesRequest.class, ((request, response, requestBean) -> {
            if (!VerifyUtils.isMobile(requestBean.tel)) {
                return error("手机号格式不对");
            }
            String salesMan = UserDataHelper.instance().getSalesManIdByMobile(requestBean.tel);
            if (!StringUtils.isEmpty(salesMan)) {
                return error("该手机号已经存在");
            }
            AdminDataManager.instance().addNewSalesMan(requestBean.name, requestBean.tel);
            return success("添加成功");
        }));

        post("addInnerNews", AddNewsRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().pushInnerNews(requestBean.title, requestBean.content);
            return success();
        }));

        post("deleteInnerNews", NewsDetailRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().deleteInnerNews(requestBean.id);
            return success();
        }));

        post("editInnerNews", EditNewsRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().editInnerNews(requestBean.id, requestBean.title, requestBean.content);
            return success();
        }));

        post("addNews", AddNewsRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().pushNews(requestBean.title, requestBean.content);
            return success();
        }));

        post("deleteNews", NewsDetailRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().deleteNews(requestBean.id);
            return success();
        }));

        post("editNews", EditNewsRequest.class, ((request, response, requestBean) -> {
            NewsDataHelper.instance().editNews(requestBean.id, requestBean.title, requestBean.content);
            return success();
        }));

        post("addRecruit", AddRecruitRequest.class, ((request, response, requestBean) -> {
            RecruitDetail recruitDetail = new RecruitDetail();
            recruitDetail.companyName = requestBean.companyName;
            recruitDetail.description = requestBean.description;
            recruitDetail.id = IdUtils.id();
            recruitDetail.place = requestBean.place;
            recruitDetail.position = requestBean.position;
            recruitDetail.salary = requestBean.salary;
            recruitDetail.tel = requestBean.tel;
            recruitDetail.welfare = requestBean.welfare;
            recruitDetail.pushTime = System.currentTimeMillis();
            RecruitDataManager.instance().pushRecruit(recruitDetail);
            return success();
        }));

        post("deleteRecruit", NewsDetailRequest.class, ((request, response, requestBean) -> {
            RecruitDataManager.instance().deleteRecruit(requestBean.id);
            return success();
        }));

        post("editRecruit", EditRecruitRequest.class, ((request, response, requestBean) -> {
            RecruitDetail recruitDetail = new RecruitDetail();
            recruitDetail.companyName = requestBean.companyName;
            recruitDetail.description = requestBean.description;
            recruitDetail.id = requestBean.id;
            recruitDetail.place = requestBean.place;
            recruitDetail.position = requestBean.position;
            recruitDetail.salary = requestBean.salary;
            recruitDetail.tel = requestBean.tel;
            recruitDetail.welfare = requestBean.welfare;
            recruitDetail.pushTime = System.currentTimeMillis();
            RecruitDataManager.instance().editRecruit(recruitDetail);
            return success();
        }));

        get("siteInfo", SiteInfoRequest.class, ((request, response, requestBean) -> {
            if (requestBean.type == 0) {
                return success(AdminDataManager.instance().getSiteInfoForBuyFromTrans(requestBean.startTime, requestBean.endTime));
            } else if (requestBean.type == 1) {
                return success(AdminDataManager.instance().getSiteInfoForOrderFromTrans(requestBean.startTime, requestBean.endTime));
            } else {
                return success(AdminDataManager.instance().getSiteInfoAllTrans(requestBean.startTime, requestBean.endTime));
            }
        }));

        get("qtList", AdminQtRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            pageBuilder.setTime(requestBean.startTime, requestBean.endTime);
            if (requestBean.status != -1) {
                pageBuilder.addEqualWhere("status", requestBean.status);
            }

            if (requestBean.salesId != -1) {
                pageBuilder.addEqualWhere("salesmanId", requestBean.salesId);
            }

            if (!StringUtils.isEmpty(requestBean.salesMobile)) {
                String salesManID = UserDataHelper.instance().getSalesManIdByMobile(requestBean.salesMobile);
                salesManID = salesManID == null ? "" : salesManID;
                pageBuilder.addEqualWhereCanEmpty("salesmanId", salesManID);
            }

            if (!StringUtils.isEmpty(requestBean.userMobile)) {
                String buyerId = UserDataHelper.instance().getUserIdByMobile(requestBean.userMobile);
                buyerId = buyerId == null ? "" : buyerId;
                pageBuilder.addEqualWhereCanEmpty("userId", buyerId);
            }

            return success(AdminDataManager.instance().getQtListResponse(pageBuilder));
        }));

        // need_merge
        get("offerDetail", AdminDetailRequest.class, ((request, response, requestBean) -> {
            AdminIronBuyDetailResponse data = new AdminIronBuyDetailResponse();
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.id);
            data.supplies = IronDataHelper.getIronDataHelper().getIronBuySupplies(requestBean.id);
            if (ironBuyBrief != null && data.supplies != null) {
                for(SupplyBrief brief : data.supplies) {
                    if (StringUtils.equals(ironBuyBrief.supplyUserId, brief.sellerId)) {
                        brief.isWinner = true;
                    }
                }
            }
            data.missSupplies = IronDataHelper.getIronDataHelper().getIronBuySuppliesMissed(requestBean.id);
            return success(data);
        }));

        post("changeSellerAccount", ChangeAccountRequest.class, ((request, response, requestBean) -> {
            UserProfile userProfile = UserDataHelper.instance().getUserProfile(requestBean.userId);
            if (userProfile == null) {
                return error("用户不存在");
            }
            if (userProfile.sellerData == null) {
                return error("用户不是商户");
            }
            if (!VerifyUtils.isMobile(requestBean.newTel) || !VerifyUtils.isMobile(requestBean.oldTel)) {
                return error("手机号格式不正确");
            }
            if (StringUtils.equals(requestBean.oldTel, requestBean.newTel)) {
                return error("新老手机号一样!");
            }
            if (!StringUtils.equals(requestBean.newPass, requestBean.newPassConfirm)) {
                return error("密码输入不一致!");
            }
            User user = UserDataHelper.instance().findUserByMobile(requestBean.newTel);
            if (user != null) {
                return error("此手机号已经被用作用户账号，无法重新绑定");
            }
            AdminDataManager.instance().changeSellerAccount(requestBean.userId, requestBean.newTel, IdUtils.md5(requestBean.newPass));
            return success("修改成功");
        }));

        get("smallAdmins", SmallAdminPageBean.class, ((request, response, requestBean) -> {
            if (requestBean.role == 0) {
                return error("权限错误");
            }
            return success(AdminDataManager.instance().getSmallAdminListResponse(new PageBuilder(requestBean.currentPage, requestBean.pageCount).addEqualWhere("role", requestBean.role + "")));
        }));

        post("insertSmallAdmins", AdminInsertSmallAdminRequest.class, ((request, response, requestBean) -> {
            if (requestBean.role == 0) {
                return error("权限错误");
            }
            if (AdminDataManager.instance().findSmallAdminByName(requestBean.userName) != null) {
                return error("当前用户已存在");
            }
            if (!StringUtils.isEmpty(requestBean.password)) {
                requestBean.password = IdUtils.md5(requestBean.password);
            }
            AdminDataManager.instance().addNewSmallAdmin(requestBean.userName, requestBean.password, requestBean.desc, requestBean.role + "");
            return success("添加成功");
        }));

        post("updateSamllAdmins", AdminUpdateSmallAdminRequest.class, ((request, response, requestBean) -> {
            if (AdminDataManager.instance().findSmallAdminByName(requestBean.userName) == null) {
                return error("当前用户不存在");
            }
            if (!StringUtils.isEmpty(requestBean.password)) {
                requestBean.password = IdUtils.md5(requestBean.password);
            }
            AdminDataManager.instance().updateSmallAdmin(requestBean.id, requestBean.userName, requestBean.password, requestBean.desc, requestBean.role + "");
            return success("更新成功");
        }));

        get("logisticsRequests", LogisticsPageRequst.class, ((request1, response1, requestBean1) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean1.currentPage, requestBean1.pageCount);
            if (requestBean1.status != -1) {
                pageBuilder.addEqualWhere("status", requestBean1.status);
            }
            return success(LogisticsDataManager.instance().getLogisticsRequests(pageBuilder));
        }));

        post("logisticsAction", LogisticsActionRequst.class, ((request, response, requestBean) -> {
            if (requestBean.status != 1 && requestBean.status != 2) {
                return error("操作不合法");
            }
            LogisticsNormalBean bean = LogisticsDataManager.instance().getLogisticsById(requestBean.id);
            if (bean.status == 0 && requestBean.status == 2) {
                return error("该请求还没有处理");
            }
            if (bean.status == 2) {
                return error("该请求已经处理完成");
            }
            LogisticsDataManager.instance().upodateLogisticsStatusById(requestBean.id, requestBean.status);
            return success("处理成功");
        }));

        post("logisticsDelete", LogisticsDeleteRequst.class, ((request, response, requestBean) -> {
            LogisticsDataManager.instance().deleteLogisticsById(requestBean.id);
            return success("删除成功");
        }));
    }


    @Override
    public String getParentRoutePath() {
        return "admin";
    }
}
