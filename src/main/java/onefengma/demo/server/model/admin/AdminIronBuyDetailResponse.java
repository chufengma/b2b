package onefengma.demo.server.model.admin;

import java.util.List;

import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;

/**
 * Created by chufengma on 16/7/5.
 */
public class AdminIronBuyDetailResponse {
    public IronBuyBrief buy;
    public List<SupplyBrief> supplies;
    public List<SupplyBrief> missSupplies;
}
