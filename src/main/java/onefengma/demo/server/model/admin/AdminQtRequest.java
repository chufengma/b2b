package onefengma.demo.server.model.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;

/**
 * Created by chufengma on 16/9/4.
 */
public class AdminQtRequest extends BaseAdminPageBean {
    @NotRequired
    public int status;
    @NotRequired
    public String salesMobile;
    @NotRequired
    public int salesId = -1;
    @NotRequired
    public String userMobile;

    public long startTime;
    public long endTime;
}
