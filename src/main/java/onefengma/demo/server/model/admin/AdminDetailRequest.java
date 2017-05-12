package onefengma.demo.server.model.admin;

import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class AdminDetailRequest extends AdminAuthSession {
    public String id;

    public boolean isNotValid() {
        return !AuthHelper.isAdminLogin(request) && !AuthHelper.isBuysAdminLogin(request);
    }
}
