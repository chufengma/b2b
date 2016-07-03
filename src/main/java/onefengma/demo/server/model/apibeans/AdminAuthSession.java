package onefengma.demo.server.model.apibeans;

import onefengma.demo.server.core.request.AuthHelper;

/**
 * Created by chufengma on 16/7/3.
 */
public class AdminAuthSession extends BaseBean {

    public boolean isNotValid() {
        return !AuthHelper.isAdminLogin(request);
    }

}
