package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.order.Order;

/**
 * Created by chufengma on 16/6/18.
 */
public class OrderRequest extends AuthSession {
    public int productType;
    public String productId;
    public float count;
    public long timeLimit;

    @NotRequired
    public String message;
    @NotRequired
    public boolean isFromCar = false;
    @NotRequired
    public int carId;

    public Order generateOrder() {
        Order order = new Order();
        order.buyerId = getUserId();
        order.count = count;
        order.productId = productId;
        order.productType = productType;
        order.status = 0;
        order.sellTime = System.currentTimeMillis();
        order.timeLimit = timeLimit;
        order.message = message;
        return order;
    }
}
