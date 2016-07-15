package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminDeleteOrderRequest extends AdminAuthSession {
    public String orderId;
}
