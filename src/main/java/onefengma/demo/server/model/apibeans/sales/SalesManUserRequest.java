package onefengma.demo.server.model.apibeans.sales;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.SalesAuthPageBean;

/**
 * Created by chufengma on 16/9/2.
 */
public class SalesManUserRequest extends SalesAuthPageBean {
    @NotRequired
    public String mobile;
}
