package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.BasePageBean;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingGetRequest extends BasePageBean {
    @NotRequired
    public String handingType;
    @NotRequired
    public String cityId;
    @NotRequired
    public String sellerId;
    @NotRequired
    public String userId;
}
