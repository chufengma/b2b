package onefengma.demo.server.model.apibeans.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.order.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chufengma on 16/6/18.
 */
public class OrderAllRequest extends AuthSession {

    public long timeLimit;
    public List<OrderSingle> orders;

    @NotRequired
    public boolean isFromCar = true;

    public List<Order> generateOrders() {
        List<Order> orderList = new ArrayList<>();
        for(OrderSingle single : orders) {
            orderList.add(generateOrder(single));
        }
        return orderList;
    }

    public Order generateOrder(OrderSingle single) {
        Order order = new Order();
        order.buyerId = getUserId();
        order.count = single.count;
        order.productId = single.productId;
        order.productType = single.productType;
        order.status = 0;
        order.sellTime = System.currentTimeMillis();
        order.timeLimit = timeLimit;
        return order;
    }

    public static class OrderSingle {
        public int productType;
        public String productId;
        public float count;
        public int carId;
    }
}
