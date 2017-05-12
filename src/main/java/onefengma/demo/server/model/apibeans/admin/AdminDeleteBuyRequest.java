package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseAdminPageBean;
import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/10.
 */
public class AdminDeleteBuyRequest extends AdminAuthSession {
    public String proId;
    public int productType = 0;

    public boolean isNotValid() {
        return !AuthHelper.isAdminLogin(request) && !AuthHelper.isBuysAdminLogin(request);
    }
}
