package onefengma.demo.server.model.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class AdminUpdateSmallAdminRequest extends AdminAuthSession {
    public int id;
    public String userName;
    @NotRequired
    public String password;
    public String desc;
    @NotRequired
    public int role = 1;
}
