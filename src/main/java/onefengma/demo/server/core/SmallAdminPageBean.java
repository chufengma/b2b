package onefengma.demo.server.core;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/3.
 */
public class SmallAdminPageBean extends BaseAdminPageBean {
    @NotRequired
    public int role = 1;
}
