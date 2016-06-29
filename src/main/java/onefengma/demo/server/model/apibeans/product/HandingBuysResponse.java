package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.HandingBuyBrief;

import java.util.List;

/**
 * Created by chufengma on 16/6/29.
 */
public class HandingBuysResponse extends BasePageResponse {

    public List<HandingBuyBrief> handings;

    public HandingBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
