package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/9.
 */
public class SellerIronBuysResponse extends IronBuyResponse {

    public int offerTimes;
    public float offerWinRate;

    public SellerIronBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
