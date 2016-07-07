package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.model.order.OrderBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/3.
 */
public class MyCommingOrdersResponse extends BasePageResponse {

    public int waitForConfirm;
    public List<OrderBrief> orders;

    public MyCommingOrdersResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
