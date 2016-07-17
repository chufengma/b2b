package onefengma.demo.server.services.order;

import com.alibaba.fastjson.JSON;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.LastRecords;
import onefengma.demo.server.model.apibeans.order.*;
import onefengma.demo.server.model.apibeans.order.OrderAllRequest.OrderSingle;
import onefengma.demo.server.model.order.Order;
import onefengma.demo.server.model.product.HandingDetail;
import onefengma.demo.server.model.product.IronDetail;
import onefengma.demo.server.services.products.HandingDataHelper;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.SellerDataHelper;
import onefengma.demo.server.services.user.UserDataHelper;

import java.util.List;

/**
 * Created by chufengma on 16/6/18.
 */
public class OrderManager extends BaseManager{

    @Override
    public void init() {
        get("lastRecords", BaseBean.class, ((request, response, requestBean) -> {
            // just for test
            // return success(OrderDataHelper.instance().getLastRecords());
            LastRecords lastRecords = new LastRecords();
            lastRecords.weight = 12440889;
            lastRecords.count = 1231;
            lastRecords.sellingMoney = 198823;
            return  success(lastRecords);
        }));

        get("orderDynamic", BaseBean.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance().getOrdersDynamic());
        }));

        post("translate", OrderRequest.class, ((request, response, requestBean) -> {
            String checkResult = orderCheck(requestBean.productId, requestBean.productType, requestBean.getUserId());
            if (!StringUtils.isEmpty(checkResult)) {
                return error(checkResult);
            }

            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                float price = 0;
                if (requestBean.productType == 0) {
                    price = IronDataHelper.getIronDataHelper().getIronPrice(requestBean.productId);
                } else {
                    price = HandingDataHelper.getHandingDataHelper().getHandingPrice(requestBean.productId);
                }
                if (price * requestBean.count >= 5000) {
                    return error("您不是企业用户，请前往后台点击成为商家上传公司三证等相关资料");
                }
            }

            OrderDataHelper.instance().translate(requestBean.generateOrder(), requestBean.isFromCar);
            return success();
        }));

        post("translateAll", OrderAllRequest.class, ((request, response, requestBean) -> {
            if (!SellerDataHelper.instance().isSeller(requestBean.getUserId())) {
                for(OrderSingle order : requestBean.orders) {
                    float price = 0;
                    if (order.productType == 0) {
                        price = IronDataHelper.getIronDataHelper().getIronPrice(order.productId);
                    } else {
                        price = HandingDataHelper.getHandingDataHelper().getHandingPrice(order.productId);
                    }
                    if (price * order.count >= 5000) {
                        return error("您不是企业用户，请前往后台点击成为商家上传公司三证等相关资料");
                    }
                }
            }
            List<Order> orders = requestBean.generateOrders();
            for(Order order : orders) {
                String checkResult = orderCheck(order.productId, order.productType, requestBean.getUserId());
                if (!StringUtils.isEmpty(checkResult)) {
                    return error(checkResult);
                }
                OrderDataHelper.instance().translate(order, true);
            }
            return success();
        }));

        post("deleteCar", CarDeleteRequest.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isCarIsUser(requestBean.carId, requestBean.getUserId())) {
                 return error("非法操作");
            }
            OrderDataHelper.instance().deleteCar(requestBean.carId, requestBean.getUserId());
            return success();
        }));

        get("myOrders", MyOrderRequest.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance()
                    .getMyOrders(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));

        post("vote", VoteOrderRequest.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderUserRight(requestBean.getUserId(), requestBean.orderId)) {
                return error("无权限操作");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status == 0) {
                return error("未确认, 无法评价");
            } else if (status == 2) {
                return error("已评价,无法再次评价");
            }
            if (!StringUtils.equals(requestBean.getUserId(), OrderDataHelper.instance().getBuyerId(requestBean.orderId))) {
                return error("用户错误,无法评价");
            }
            OrderDataHelper.instance().voteOrder(requestBean.orderId, requestBean.vote);
            return success();
        }));

        post("delete", OrderDeleteRequest.class, ((request, response, requestBean) -> {
            if (!OrderDataHelper.instance().isOrderUserRight(requestBean.getUserId(), requestBean.orderId)) {
                return error("无权限操作");
            }
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status != 1 && status != 2) {
                return error("该订单无法删除");
            }
            if (!StringUtils.equals(requestBean.getUserId(), OrderDataHelper.instance().getBuyerId(requestBean.orderId))) {
                return error("用户错误, 无法删除");
            }
            OrderDataHelper.instance().deleteOrder(requestBean.orderId, false);
            return success();
        }));

        post("addToCar", OrderCarAddRequest.class, ((request, response, requestBean) -> {
            String checkResult = orderCheck(requestBean.proId, requestBean.productType, requestBean.getUserId());
            if (!StringUtils.isEmpty(checkResult)) {
                return error(checkResult);
            }
            OrderDataHelper.instance().addToCar(requestBean.getUserId(), requestBean.proId, requestBean.productType);
            return success();
        }));

        get("myCars", BaseAuthPageBean.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance()
                    .getMyCars(new PageBuilder(requestBean.currentPage, requestBean.pageCount).addEqualWhere("userId", requestBean.getUserId())));
        }));

        get("myCarsCount", AuthSession.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance().getCarCount(requestBean.getUserId()));
        }));
    }

    private String orderCheck(String proId, int productType, String userId) throws NoSuchFieldException, IllegalAccessException {
        if (productType == 0) {
            IronDetail ironDetail = IronDataHelper.getIronDataHelper().getIronProductById(proId);
            if (ironDetail == null) {
                return "加入购物车出错, 没有该商品";
            }
            if (StringUtils.equals(ironDetail.userId, userId)) {
                return "加入购物车出错, 无法购买自己店铺商品";
            }
        } else {
            HandingDetail handingDetail = HandingDataHelper.getHandingDataHelper().getHandingProductById(proId);
            if (handingDetail == null) {
                return "加入购物车出错, 没有该商品";
            }
            if (StringUtils.equals(handingDetail.userId, userId)) {
                return "加入购物车出错, 无法购买自己店铺商品";
            }
        }

        return null;
    }

    @Override
    public String getParentRoutePath() {
        return "order";
    }

}
