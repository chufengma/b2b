package onefengma.demo.server.model.admin;

import onefengma.demo.server.services.user.AdminDataManager;
import onefengma.demo.server.services.user.AdminDataManager.BuyerBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/3.
 */
public class AdminUsersResponse extends BaseAdminPageResponse{
    public List<BuyerBrief> buyers;
}
