package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.BasePageBean;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronsGetRequest extends BasePageBean {
    @NotRequired
    public String material;
    @NotRequired
    public String surface;
    @NotRequired
    public String proPlace;
    @NotRequired
    public String ironType;
    @NotRequired
    public String sellerId;
    @NotRequired
    public String userId;
    @NotRequired
    public String cityId;
    @NotRequired
    public Boolean isSpec;
}
