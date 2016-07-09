package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.HandingBuyBrief;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.services.products.IronDataHelper.SellerOffer;

/**
 * Created by chufengma on 16/7/9.
 */
public class SellerHandingBuyDetailResponse {
    public String salesManPhone;
    public HandingBuyBrief buy;
    public SellerOffer myOffer;
}
