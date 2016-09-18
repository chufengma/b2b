package onefengma.demo.server.services.user;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.apibeans.SalesAuthPageBean;
import onefengma.demo.server.model.apibeans.login.Login;
import onefengma.demo.server.model.apibeans.sales.SalesIronBuyDetailResponse;
import onefengma.demo.server.model.apibeans.sales.SalesIronDetalRequest;
import onefengma.demo.server.model.apibeans.sales.SalesManUserRequest;
import onefengma.demo.server.model.apibeans.sales.SalesQtStatusRequest;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.model.qt.QtDetail;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.SalesDataHelper.SalesManDetail;
import spark.Session;

/**
 * @author yfchu
 * @date 2016/9/1
 */
public class SalesManager extends BaseManager {

    @Override
    public void init() {
        post("login", Login.class, ((request, response, requestBean) -> {
            SalesManDetail salesManDetail = SalesDataHelper.instance().getSalesMan(requestBean.mobile);
            if (salesManDetail == null) {
                return error("没有该专员");
            }
            if (StringUtils.equalsIngcase(IdUtils.md5(requestBean.password), salesManDetail.password)) {
                Session session = request.session();
                session.attribute("sales", salesManDetail.id + "");
                session.maxInactiveInterval(30 * 60 * 24);
                response.cookie("sales", salesManDetail.id + "", 30 * 60 * 24);

                // clean password
                salesManDetail.password = "";
                return success(salesManDetail);
            } else {
                return error("密码不正确");
            }
        }));

        get("bindUsers", SalesManUserRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            if (!StringUtils.isEmpty(requestBean.mobile)) {
                pageBuilder.addLikeWhere("mobile", requestBean.mobile);
            }
            return success(SalesDataHelper.instance().getBindUserResponse(requestBean.getSalesId(), pageBuilder));
        }));

        get("bindSellers", SalesManUserRequest.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            if (!StringUtils.isEmpty(requestBean.mobile)) {
                pageBuilder.addLikeWhere("mobile", requestBean.mobile);
            }
            if (!StringUtils.isEmpty(requestBean.companyName)) {
                pageBuilder.addLikeWhere("companyName", requestBean.companyName);
            }
            return success(SalesDataHelper.instance().getBindSellerResponse(requestBean.getSalesId(), pageBuilder));
        }));

        get("qtList", SalesAuthPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            pageBuilder.addEqualWhere("salesmanId", requestBean.getSalesId());
            pageBuilder.addEqualWhere("status", requestBean.status);
            return success(SalesDataHelper.instance().qtList(pageBuilder));
        }));

        get("ironDetail", SalesIronDetalRequest.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusByIronId(requestBean.ironId);

            SalesIronBuyDetailResponse myIronBuyDetailResponse = new SalesIronBuyDetailResponse();
            // reset iron new offer count
            IronDataHelper.getIronDataHelper().resetIronBuyNewOffersCount(requestBean.ironId);

            myIronBuyDetailResponse.buy = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            myIronBuyDetailResponse.supplies = IronDataHelper.getIronDataHelper().getIronBuySupplies(requestBean.ironId);
            myIronBuyDetailResponse.sellerInfo = SellerDataHelper.instance().getSeller(myIronBuyDetailResponse.buy.userId);
            myIronBuyDetailResponse.missSupplies = IronDataHelper.getIronDataHelper().getIronBuySuppliesMissed(requestBean.ironId);

            if (myIronBuyDetailResponse.supplies!= null
                    && myIronBuyDetailResponse.buy != null) {
                for(SupplyBrief supplyBrief : myIronBuyDetailResponse.supplies) {
                    if (StringUtils.equals(myIronBuyDetailResponse.buy.supplyUserId, supplyBrief.sellerId)) {
                        supplyBrief.isWinner = true;
                    }
                    supplyBrief.mobile = UserDataHelper.instance().getUserMobile(supplyBrief.sellerId);
                }
            }

            if (myIronBuyDetailResponse.missSupplies != null) {
                for(SupplyBrief supplyBrief : myIronBuyDetailResponse.missSupplies) {
                    supplyBrief.mobile = UserDataHelper.instance().getUserMobile(supplyBrief.sellerId);
                }
            }

            SalesMan salesMan = UserDataHelper.instance().getSalesManById(Integer.parseInt(requestBean.getSalesId()));
            myIronBuyDetailResponse.salesMan = salesMan;
            myIronBuyDetailResponse.salesManPhone = salesMan == null ? "" : salesMan.tel;
            myIronBuyDetailResponse.qtDetail = SalesDataHelper.instance().getQtDetail(requestBean.ironId);
            myIronBuyDetailResponse.userInfo = SalesDataHelper.instance().getUserInfo(myIronBuyDetailResponse.buy.userId);



            return success(myIronBuyDetailResponse);
        }));

        post("updateQtStatus", SalesQtStatusRequest.class, ((request, response, requestBean) -> {
            if (requestBean.status != 1 && requestBean.status != 2 && requestBean.status != 3) {
                return error("无效操作");
            }
            QtDetail qtDetail = SalesDataHelper.instance().getQtDetailByQtId(requestBean.qtId);

            // 开始质检 或 取消质检
            if (requestBean.status == 3 || requestBean.status == 2) {
                if (qtDetail.status != 0) {
                    return error("此次质检无法该操作");
                }
            }

            if (requestBean.status == 1) {
                if (qtDetail.status != 3) {
                    return error("此次质检无法操作该操作");
                }
            }

            SalesDataHelper.instance().updateQtStatus(requestBean.qtId, requestBean.status);
            return success("操作成功");
        }));

        get("buyList", SalesAuthPageBean.class, ((request, response, requestBean) -> {
            PageBuilder pageBuilder = new PageBuilder(requestBean.currentPage, requestBean.pageCount);
            pageBuilder.addEqualWhere("status", requestBean.status);
            return success(SalesDataHelper.instance().getSalesIronBuy(requestBean.getSalesId(), pageBuilder));
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "sales";
    }

}
