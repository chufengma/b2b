package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.request.AuthHelper;

/**
 * Created by chufengma on 16/7/3.
 */
public class SalesAuthSession extends BaseBean {

    @NotRequired
    public String salesId;

    public String getSalesId() {
        return salesId;
    }

    public boolean isNotValid() {
        salesId = request.cookie("sales");
        return !AuthHelper.isSalesLogin(request);
    }

}
