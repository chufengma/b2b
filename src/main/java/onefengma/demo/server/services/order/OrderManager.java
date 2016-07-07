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
import onefengma.demo.server.model.order.Order;

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
            OrderDataHelper.instance().translate(requestBean.generateOrder(), requestBean.isFromCar);
            return success();
        }));

        post("translateALl", OrderAllRequest.class, ((request, response, requestBean) -> {
            List<Order> orders = requestBean.generateOrders();
            for(Order order : orders) {
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

    @Override
    public String getParentRoutePath() {
        return "order";
    }

}
