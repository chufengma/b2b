package onefengma.demo.server.model.apibeans;

import onefengma.demo.server.core.request.AuthHelper;

/**
 * Created by chufengma on 16/7/3.
 */
public class SalesAuthSession extends BaseBean {

    public boolean isNotValid() {
        return !AuthHelper.isSalesLogin(request);
    }

}
