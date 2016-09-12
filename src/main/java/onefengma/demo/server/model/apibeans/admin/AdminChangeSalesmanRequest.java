package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminChangeSalesmanRequest extends AdminAuthSession {
    public int id;
    @NotRequired
    public String mobile;
    @NotRequired
    public String name;
    @NotRequired
    public String password;
}
