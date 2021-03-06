package onefengma.demo.server.services.order;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.core.UpdateBuilder;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.UpdateSellerRequest;
import onefengma.demo.server.model.apibeans.UpdateSubscribeInfoRequest;
import onefengma.demo.server.model.apibeans.order.ConfirmSellerOrder;
import onefengma.demo.server.model.apibeans.product.OfferHandingRequest;
import onefengma.demo.server.model.apibeans.product.OfferIronRequest;
import onefengma.demo.server.model.apibeans.product.SellerHandingBuyDetailRequest;
import onefengma.demo.server.model.apibeans.product.SellerHandingBuyDetailResponse;
import onefengma.demo.server.model.apibeans.product.SellerIronBuyDetailRequest;
import onefengma.demo.server.model.apibeans.product.SellerIronBuyDetailResponse;
import onefengma.demo.server.model.metaData.City;
import onefengma.demo.server.model.product.HandingBuyBrief;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.services.funcs.CityDataHelper;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;
import onefengma.demo.server.services.user.UserMessageDataHelper;

/**
 * Created by chufengma on 16/7/7.
 */
public class SellerManager extends BaseManager {

    @Override
    public void init() {

        get("commingOrders", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            OrderDataHelper.instance().updateOrderStatusBySeller(requestBean.getUserId());
            return success(OrderDataHelper.instance().getCommingOrders(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));

        post("confirmOrder", ConfirmSellerOrder.class, ((request, response, requestBean) -> {
            OrderDataHelper.instance().updateOrderStatusByOrderId(requestBean.orderId);

            if (!OrderDataHelper.instance().isOrderSellerRight(requestBean.orderId, requestBean.getUserId())) {
                return error("用户权限错误");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status != 0) {
                return error("该订单无法确认");
            }
            OrderDataHelper.instance().confirmOrder(requestBean.orderId);
            String buyerId = OrderDataHelper.instance().getBuyerId(requestBean.orderId);
            if (!StringUtils.isEmpty(buyerId)) {
                Seller seller = SellerDataHelper.instance().getSeller(requestBean.getUserId());
                UserMessageDataHelper.instance().setUserMessage(buyerId, seller.companyName + "公司已经确认接单，订单号：" + requestBean.orderId + " 已成交，请联系对方");
            }
            return success();
        }));

        post("cancelOrder", ConfirmSellerOrder.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderSellerRight(requestBean.orderId, requestBean.getUserId())) {
                return error("用户权限错误");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status != 0) {
                return error("该订单无法忽略");
            }
            OrderDataHelper.instance().concelOrder(requestBean.orderId);
            return success();
        }));

        post("deleteOrder", ConfirmSellerOrder.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderSellerRight(requestBean.orderId, requestBean.getUserId())) {
                return error("用户权限错误");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status != 1 && status != 2) {
                return error("该订单无法删除");
            }
            OrderDataHelper.instance().deleteOrder(requestBean.orderId, true);
            return success();
        }));

        get("myIronBuys", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusBySellerId(requestBean.getUserId());

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("非商家用户");
            }
            return success(IronDataHelper.getIronDataHelper()
                    .getSellerIronBuys(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId(), requestBean.status));
        }));

        get("myIronBuyDetail", SellerIronBuyDetailRequest.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusBySellerId(requestBean.getUserId());

            SellerIronBuyDetailResponse sellerIronBuyDetailResponse = new SellerIronBuyDetailResponse();
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            sellerIronBuyDetailResponse.buy = ironBuyBrief;

            String owerUserId = SellerDataHelper.instance().getBuyUserId(ironBuyBrief.id, 0);
            SalesMan salesMan = UserDataHelper.instance().getSalesMan(owerUserId);
            if (salesMan != null) {
                sellerIronBuyDetailResponse.salesManPhone = salesMan.tel;
            }

            sellerIronBuyDetailResponse.myOffer = IronDataHelper.getIronDataHelper().getSellerOffer(requestBean.getUserId(), requestBean.ironId);

            // 我已中标
            if (ironBuyBrief != null && ironBuyBrief.status == 1 && StringUtils.equals(ironBuyBrief.supplyUserId, requestBean.getUserId())) {
                ironBuyBrief.status = 4;
                sellerIronBuyDetailResponse.buyerMobile = UserDataHelper.instance().getUserMobile(ironBuyBrief.userId);
            }

            // 候选中
            if (ironBuyBrief != null && ironBuyBrief.status == 0 && sellerIronBuyDetailResponse.myOffer != null) {
                ironBuyBrief.status = 3;
            }

            // 流标
            if (sellerIronBuyDetailResponse.myOffer != null && ironBuyBrief.status == 1) {
                ironBuyBrief.status = 6;
            }

            sellerIronBuyDetailResponse.userBuyInfo = SellerDataHelper.instance().getUserBuyInfo(ironBuyBrief.userId);
            sellerIronBuyDetailResponse.buyerSeller = SellerDataHelper.instance().getSeller(ironBuyBrief.userId);

            return success(sellerIronBuyDetailResponse);
        }));

        post("missIronBuyOffer", SellerIronBuyDetailRequest.class, ((request, response, requestBean) -> {
            int status = IronDataHelper.getIronDataHelper().getIronBuyStatus(requestBean.ironId);
            if (status != 0) {
                return error("该求购无法忽略");
            }
            IronDataHelper.getIronDataHelper().missIronBuyOffer(requestBean.ironId, requestBean.getUserId());
            return success("操作成功");
        }));

        post("offerIronBuy", OfferIronRequest.class, ((request, response, requestBean) -> {
            IronDataHelper.getIronDataHelper().updateBuyStatusByIronId(requestBean.ironId);

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("仅商家才能进行报价");
            }
            if (!IronDataHelper.getIronDataHelper().isIronBuyExisted(requestBean.ironId)) {
                return error("该求购不存在");
            }
            int status = IronDataHelper.getIronDataHelper().getIronBuyStatus(requestBean.ironId);
            if (status != 0) {
                return error("该求购已取消，无法报价");
            }
            if (IronDataHelper.getIronDataHelper().isOffered(requestBean.getUserId(), requestBean.ironId)) {
                return error("您已报价");
            }
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            if (ironBuyBrief != null) {
                if (StringUtils.equals(ironBuyBrief.userId, requestBean.getUserId())) {
                    return error("无法对自己的求购进行报价");
                }
            }

            IronDataHelper.getIronDataHelper().offerIronBuy(requestBean.getUserId(), requestBean.ironId, requestBean.price, requestBean.msg, requestBean.unit);
            UserMessageDataHelper.instance().setUserMessage(ironBuyBrief.userId, "有人报价您的不锈钢求购 请去【后台管理--不锈钢报价管理】刷新查看。");
            return success();
        }));

        get("myHandingBuys", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateBuyStatusBySellerId(requestBean.getUserId());

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("非商家用户");
            }
            return success(HandingDataHelper.getHandingDataHelper()
                    .getSellerHandingBuys(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));


        get("myHandingBuyDetail", SellerHandingBuyDetailRequest.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateBuyStatusBySellerId(requestBean.getUserId());

            SellerHandingBuyDetailResponse sellerHandingBuyDetailResponse = new SellerHandingBuyDetailResponse();
            HandingBuyBrief buyBrief = HandingDataHelper.getHandingDataHelper().getHandingBrief(requestBean.handingId);
            sellerHandingBuyDetailResponse.buy = buyBrief;

            String owerUserId = SellerDataHelper.instance().getBuyUserId(buyBrief.id, 1);
            SalesMan salesMan = UserDataHelper.instance().getSalesMan(owerUserId);
            if (salesMan != null) {
                sellerHandingBuyDetailResponse.salesManPhone = salesMan.tel;
            }

            sellerHandingBuyDetailResponse.myOffer = HandingDataHelper.getHandingDataHelper().getSellerOffer(requestBean.getUserId(), requestBean.handingId);

            // 我已中标
            if (buyBrief != null && buyBrief.status == 1 && StringUtils.equals(buyBrief.supplyUserId, requestBean.getUserId())) {
                buyBrief.status = 4;
                sellerHandingBuyDetailResponse.buyerMobile = UserDataHelper.instance().getUserMobile(buyBrief.userId);
            }

            // 候选中
            if (buyBrief != null && buyBrief.status == 0 && sellerHandingBuyDetailResponse.myOffer != null) {
                buyBrief.status = 3;
            }

            return success(sellerHandingBuyDetailResponse);
        }));


        post("offerHandingBuy", OfferHandingRequest.class, ((request, response, requestBean) -> {
            HandingDataHelper.getHandingDataHelper().updateBuyStatusByHandingId(requestBean.handingId);

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("仅商家才能进行报价");
            }
            if (!HandingDataHelper.getHandingDataHelper().isHandingBuyExisted(requestBean.handingId)) {
                return error("该求购不存在");
            }
            int status = HandingDataHelper.getHandingDataHelper().getHandingBuyStatus(requestBean.handingId);
            if (status != 0) {
                return error("该订单无法报价");
            }
            if (HandingDataHelper.getHandingDataHelper().isOffered(requestBean.getUserId(), requestBean.handingId)) {
                return error("您已报价");
            }
            HandingBuyBrief handingBuyBrief = HandingDataHelper.getHandingDataHelper().getHandingBrief(requestBean.handingId);
            if (handingBuyBrief != null) {
                if (StringUtils.equals(handingBuyBrief.userId, requestBean.getUserId())) {
                    return error("无法对自己的求购进行报价");
                }
            }
            HandingDataHelper.getHandingDataHelper().offerHandingBuy(requestBean.getUserId(), requestBean.handingId, requestBean.price, requestBean.msg, requestBean.unit);
            return success();
        }));

        get("profile", AuthSession.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("无权限操作");
            }
            return success(SellerDataHelper.instance().getSeller(requestBean.getUserId()));
        }));

        multiPost("updateProfile", UpdateSellerRequest.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("无权限操作");
            }

            if (!StringUtils.isEmpty(requestBean.cantactTel)) {
                if (!VerifyUtils.isMobile(requestBean.cantactTel)) {
                    return errorAndClear(requestBean, "手机号码输入不正确");
                }
            }

            if (!StringUtils.isEmpty(requestBean.cityId)) {
                City city = CityDataHelper.instance().getCityById(requestBean.cityId);
                if (city == null) {
                    return errorAndClear(requestBean, "城市选择有误");
                }
            }

            UpdateBuilder updateBuilder = new UpdateBuilder();
            updateBuilder.addStringMap("cantactTel", requestBean.cantactTel);
            updateBuilder.addStringMap("cityId", requestBean.cityId);
            updateBuilder.addStringMap("contact", requestBean.contact);
            updateBuilder.addStringMap("fax", requestBean.fax);
            updateBuilder.addStringMap("officeAddress", requestBean.officeAddress);
            updateBuilder.addStringMap("qq", requestBean.qq);
            updateBuilder.addStringMap("shopProfile", requestBean.shopProfile);
            updateBuilder.addStringMap("cover", requestBean.cover == null ? "" : FileHelper.generateRelativeInternetUri(requestBean.cover));
            SellerDataHelper.instance().updateSeller(updateBuilder, requestBean.getUserId());
            return success();
        }));


        get("myIntegral", AuthSession.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("用户权限错误");
            }
            return success(SellerDataHelper.instance().getSellerIntegral(requestBean.getUserId()));
        }));

        get("sellerCenterData", AuthSession.class, ((request, response, requestBean) -> {
            return success(UserDataHelper.instance().getSellerInfo(requestBean.getUserId()));
        }));

        get("subscribeInfo", AuthSession.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("用户权限错误");
            }
            return success(SellerDataHelper.instance().getSunscribeInfo(requestBean.getUserId()));
        }));

        post("subscribeInfo", UpdateSubscribeInfoRequest.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("用户权限错误");
            }

            SellerDataHelper.instance().updateSunscribeInfo(requestBean.getUserId(),
                    requestBean.types, requestBean.surfaces,
                    requestBean.materials, requestBean.proPlaces);

            return success("更新关注成功");
        }));

    }

    @Override
    public String getParentRoutePath() {
        return "seller";
    }
}
