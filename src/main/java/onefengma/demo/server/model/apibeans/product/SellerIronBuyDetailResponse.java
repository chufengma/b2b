package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.products.IronDataHelper.SellerOffer;

/**
 * Created by chufengma on 16/7/9.
 */
public class SellerIronBuyDetailResponse {
    public String salesManPhone;
    public IronBuyBrief buy;
    public SellerOffer myOffer;
}
