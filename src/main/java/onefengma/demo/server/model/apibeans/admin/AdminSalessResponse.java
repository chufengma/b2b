package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.server.model.admin.BaseAdminPageResponse;
import onefengma.demo.server.services.user.AdminDataManager;
import onefengma.demo.server.services.user.AdminDataManager.OrderForAdmin;
import onefengma.demo.server.services.user.AdminDataManager.SalesManAdmin;

import java.util.List;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminSalessResponse extends BaseAdminPageResponse {
    public List<SalesManAdmin> sales;
}