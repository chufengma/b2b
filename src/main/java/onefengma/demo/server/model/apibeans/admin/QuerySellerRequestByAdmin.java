package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/6/5.
 */
public class QuerySellerRequestByAdmin extends AdminAuthSession {
    public String userMobile;

    public boolean isNotValid() {
        return !AuthHelper.isAdminLogin(request) && !AuthHelper.isPushBuyAdminLogin(request);
    }
}
