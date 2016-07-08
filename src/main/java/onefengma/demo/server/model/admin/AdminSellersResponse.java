package onefengma.demo.server.model.admin;

import onefengma.demo.server.services.user.AdminDataManager;
import onefengma.demo.server.services.user.AdminDataManager.BuyerBrief;
import onefengma.demo.server.services.user.AdminDataManager.SellerBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/3.
 */
public class AdminSellersResponse extends BaseAdminPageResponse{
    public List<SellerBrief> sellers;
}
