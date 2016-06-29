package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.IronProductBrief;

import java.util.List;

/**
 * Created by chufengma on 16/6/29.
 */
public class IronBuyResponse extends BasePageResponse {

    public List<IronBuyBrief> buys;

    public IronBuyResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
