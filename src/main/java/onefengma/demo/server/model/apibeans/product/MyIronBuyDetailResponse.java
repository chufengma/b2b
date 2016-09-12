package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;

/**
 * Created by chufengma on 16/7/5.
 */
public class MyIronBuyDetailResponse {
    public IronBuyBrief buy;
    public List<SupplyBrief> supplies;
    public List<SupplyBrief> missSupplies;
    public String salesManPhone;
    public SalesMan salesMan;
}
