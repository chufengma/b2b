package onefengma.demo.server.services.order;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;
import onefengma.demo.server.model.apibeans.order.ConfirmSellerOrder;

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
    }

    @Override
    public String getParentRoutePath() {
        return "seller";
    }
}
