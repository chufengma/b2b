package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminBuysRequest extends BaseAdminPageBean {
    @NotRequired
    public String buyId;
    @NotRequired
    public String buyerMobile;
    @NotRequired
    public String sellerMobile;
    @NotRequired
    public String buyerCompanyName;
    @NotRequired
    public String sellerCompanyName;
    @NotRequired
    public String salesManMobile;
    @NotRequired
    public int productType = 0;
    @NotRequired
    public int status = -1;
}
