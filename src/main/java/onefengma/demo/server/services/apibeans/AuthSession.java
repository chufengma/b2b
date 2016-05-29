package onefengma.demo.server.services.apibeans;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.model.AuthData;
import onefengma.demo.server.core.request.AuthHelper;
import spark.Request;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class AuthSession extends BaseBean {

    @NotRequired
    private AuthData serverData;
    @NotRequired
    private AuthData clientData;

    public void setAuthData(Request request) {
        serverData = new AuthData(AuthHelper.getServerToken(request), AuthHelper.getServerUserId(request));
        clientData = new AuthData(AuthHelper.getRequestToken(request), AuthHelper.getRequestUserId(request));
    }

    public boolean isNotValid() {
        return serverData == null || !serverData.equals(clientData);
    }
}
