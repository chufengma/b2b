package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.model.order.OrderBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/3.
 */
public class MyOrdersResponse extends BasePageResponse {

    public int waitForConfirm;
    public int waitForVote;
    public List<OrderBrief> orders;

    public MyOrdersResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
