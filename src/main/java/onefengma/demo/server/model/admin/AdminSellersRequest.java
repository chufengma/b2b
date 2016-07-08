package onefengma.demo.server.model.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;

/**
 * Created by chufengma on 16/7/9.
 */
public class AdminSellersRequest extends BaseAdminPageBean {
    @NotRequired
    public String userMobile;
    @NotRequired
    public String salesMobile;
    @NotRequired
    public String companyName = "";
    public long becomeSellerTimeStart;
    public long becomeSellerTimeEnd;

    @NotRequired
    public long buyTimeStart = -1;
    @NotRequired
    public long buyTimeEnd = -1;

    @NotRequired
    public long sellTimeStart = -1;
    @NotRequired
    public long sellTimeEnd = -1;

}
