package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.HandingBuyBrief;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/5.
 */
public class MyIronBuyDetailResponse {
    public IronBuyBrief buy;
    public List<SupplyBrief> supplies;
    public String salesManPhone;
}
