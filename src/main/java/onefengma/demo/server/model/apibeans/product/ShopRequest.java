package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BasePageBean;

/**
 * Created by chufengma on 16/6/26.
 */
public class ShopRequest extends BasePageBean {
    @NotRequired
    public String cityId;
    @NotRequired
    public int productType = -2 ; // 0 iron, 1 handing
}
