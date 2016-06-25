package onefengma.demo.server.model.apibeans.order;

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

    public Order generateOrder() {
        Order order = new Order();
        order.buyerId = "";
        order.count = count;
        order.productId = productId;
//        order. = ;

        return order;
    }
}
