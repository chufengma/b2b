package onefengma.demo.server.model.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/3.
 */
public class AdminUsersRequest extends BaseAdminPageBean {
    @NotRequired
    public String salesTel;
    @NotRequired
    public String userTel;
    @NotRequired
    public long time;
}
