package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminSalesRequest extends BaseAdminPageBean {
    @NotRequired
    public int salesManId = -1;
    @NotRequired
    public String salesManMobile;
    @NotRequired
    public long startTime = -1;
    @NotRequired
    public long endTime = -1;
}
