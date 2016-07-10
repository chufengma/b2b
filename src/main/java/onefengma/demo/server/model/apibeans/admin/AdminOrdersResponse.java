package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.model.admin.BaseAdminPageResponse;
import onefengma.demo.server.services.user.AdminDataManager;
import onefengma.demo.server.services.user.AdminDataManager.OrderForAdmin;

import java.util.List;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminOrdersResponse extends BaseAdminPageResponse {
    public List<OrderForAdmin> orders;
}
