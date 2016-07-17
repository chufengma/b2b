package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.request.AuthHelper;
import spark.Request;
import spark.Response;

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

    public void cleanLogin(Request request, Response response) {
        AuthHelper.cleanLoginStatus(request, response);
    }

    public boolean isNotValid() {
        return serverData == null || !serverData.equals(clientData);
    }

    public String getUserId() {
        return serverData.getUserId();
    }
}
