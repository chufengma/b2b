package onefengma.demo.server.model.apibeans.sales;

import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.model.qt.QtDetail;
import onefengma.demo.server.services.products.IronDataHelper;
import onefengma.demo.server.services.user.SalesDataHelper;
import onefengma.demo.server.services.user.SalesDataHelper.UserInfo;

import java.util.List;

/**
 * Created by chufengma on 16/7/5.
 */
public class SalesIronBuyDetailResponse {
    public IronBuyBrief buy;
    public List<SupplyBrief> supplies;
    public String salesManPhone;
    public SalesMan salesMan;
    public QtDetail qtDetail;
    public UserInfo userInfo;
}
