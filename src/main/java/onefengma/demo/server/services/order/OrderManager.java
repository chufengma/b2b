package onefengma.demo.server.services.order;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.LastRecords;
import onefengma.demo.server.model.apibeans.order.MyOrderRequest;
import onefengma.demo.server.model.apibeans.order.OrderDeleteRequest;
import onefengma.demo.server.model.apibeans.order.OrderRequest;
import onefengma.demo.server.model.apibeans.order.VoteOrderRequest;
import onefengma.demo.server.model.order.Order;

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
            OrderDataHelper.instance().translate(requestBean.generateOrder());
            return success();
        }));

        get("myOrders", MyOrderRequest.class, ((request, response, requestBean) -> {
            return success(OrderDataHelper.instance().getMyOrders(new PageBuilder(requestBean.currentPage, requestBean.pageCount), requestBean.getUserId()));
        }));

        post("vote", VoteOrderRequest.class, ((request, response, requestBean) -> {
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
            int status = OrderDataHelper.instance().getOrderStatus(requestBean.orderId);
            if (status == 0) {
                return error("未确认, 无法删除");
            }
            if (!StringUtils.equals(requestBean.getUserId(), OrderDataHelper.instance().getBuyerId(requestBean.orderId))) {
                return error("用户错误, 无法删除");
            }
            OrderDataHelper.instance().deleteOrder(requestBean.orderId);
            return success();
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "order";
    }

}
