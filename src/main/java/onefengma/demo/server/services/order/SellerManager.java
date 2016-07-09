package onefengma.demo.server.services.order;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.order.ConfirmSellerOrder;
import onefengma.demo.server.model.apibeans.product.*;
import onefengma.demo.server.model.product.HandingBuyBrief;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.products.IronManager;
import onefengma.demo.server.services.user.*;

/**
 * Created by chufengma on 16/7/7.
 */
public class SellerManager extends BaseManager {

    @Override
    public void init() {

        get("commingOrders", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance().getCommingOrders(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));

        post("confirmOrder", ConfirmSellerOrder.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderSellerRight(requestBean.orderId, requestBean.getUserId())) {
                return error("用户权限错误");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status != 0) {
                return error("该订单无法确认");
            }
            OrderDataHelper.instance().confirmOrder(requestBean.orderId);
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
            if(!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("非商家用户");
            }
            return success(IronDataHelper.getIronDataHelper()
                    .getSellerIronBuys(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));

        get("myIronBuyDetail", SellerIronBuyDetailRequest.class, ((request, response, requestBean) -> {
            SellerIronBuyDetailResponse sellerIronBuyDetailResponse = new SellerIronBuyDetailResponse();
            IronBuyBrief ironBuyBrief = IronDataHelper.getIronDataHelper().getIronBuyBrief(requestBean.ironId);
            if (ironBuyBrief != null && ironBuyBrief.status == 1 && StringUtils.equals(ironBuyBrief.supplyUserId, requestBean.getUserId())) {
                ironBuyBrief.status = 4;
            }
            sellerIronBuyDetailResponse.buy = ironBuyBrief;

            String owerUserId = SellerDataHelper.instance().getBuyUserId(ironBuyBrief.id, 0);
            SalesMan salesMan = UserDataHelper.instance().getSalesMan(owerUserId);
            if (salesMan != null) {
                sellerIronBuyDetailResponse.salesManPhone = salesMan.tel;
            }

            sellerIronBuyDetailResponse.myOffer = IronDataHelper.getIronDataHelper().getSellerOffer(requestBean.getUserId(), requestBean.ironId);

            return success(sellerIronBuyDetailResponse);
        }));

        post("offerIronBuy", OfferIronRequest.class, ((request, response, requestBean) -> {
            if(!IronDataHelper.getIronDataHelper().isIronBuyExisted(requestBean.ironId)) {
                return error("该求购不存在");
            }
            int status = IronDataHelper.getIronDataHelper().getIronBuyStatus(requestBean.ironId);
            if (status != 0) {
                return error("该订单无法报价");
            }
            if (IronDataHelper.getIronDataHelper().isOffered(requestBean.getUserId(), requestBean.ironId)) {
                return error("您已报价");
            }
            IronDataHelper.getIronDataHelper().offerIronBuy(requestBean.getUserId(), requestBean.ironId, requestBean.price, requestBean.msg);
            return success();
        }));

        get("myHandingBuys", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            if(!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                return error("非商家用户");
            }
            return success(HandingDataHelper.getHandingDataHelper()
                    .getSellerHandingBuys(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));


        get("myHandingBuyDetail", SellerHandingBuyDetailRequest.class, ((request, response, requestBean) -> {
            SellerHandingBuyDetailResponse sellerHandingBuyDetailResponse = new SellerHandingBuyDetailResponse();
            HandingBuyBrief buyBrief = HandingDataHelper.getHandingDataHelper().getHandingBrief(requestBean.handingId);
            if (buyBrief != null && buyBrief.status == 1 && StringUtils.equals(buyBrief.supplyUserId, requestBean.getUserId())) {
                buyBrief.status = 4;
            }
            sellerHandingBuyDetailResponse.buy = buyBrief;

            String owerUserId = SellerDataHelper.instance().getBuyUserId(buyBrief.id, 1);
            SalesMan salesMan = UserDataHelper.instance().getSalesMan(owerUserId);
            if (salesMan != null) {
                sellerHandingBuyDetailResponse.salesManPhone = salesMan.tel;
            }

            sellerHandingBuyDetailResponse.myOffer = HandingDataHelper.getHandingDataHelper().getSellerOffer(requestBean.getUserId(), requestBean.handingId);

            return success(sellerHandingBuyDetailResponse);
        }));


        post("offerHandingBuy", OfferHandingRequest.class, ((request, response, requestBean) -> {
            if(!HandingDataHelper.getHandingDataHelper().isHandingBuyExisted(requestBean.handingId)) {
                return error("该求购不存在");
            }
            int status = HandingDataHelper.getHandingDataHelper().getHandingBuyStatus(requestBean.handingId);
            if (status != 0) {
                return error("该订单无法报价");
            }
            if (HandingDataHelper.getHandingDataHelper().isOffered(requestBean.getUserId(), requestBean.handingId)) {
                return error("您已报价");
            }

            HandingDataHelper.getHandingDataHelper().offerHandingBuy(requestBean.getUserId(), requestBean.handingId, requestBean.price, requestBean.msg);
            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "seller";
    }
}
