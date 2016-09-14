package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.services.products.IronDataHelper.SellerOffer;
import onefengma.demo.server.services.products.IronDataHelper.UserBuyInfo;

/**
 * Created by chufengma on 16/7/9.
 */
public class SellerIronBuyDetailResponse {
    public String salesManPhone;
    public String buyerMobile;
    public IronBuyBrief buy;
    public SellerOffer myOffer;
    public UserBuyInfo userBuyInfo;
    public Seller buyerSeller;

}
