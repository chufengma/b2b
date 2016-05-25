package onefengma.demo.server.services.apibeans;

import java.util.UUID;

import onefengma.demo.common.StringUtils;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class AuthSession extends BaseBean {

    public static final String HEADER_TICKET = "auth";
    private String auth;

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSessionTicket() {
        return auth;
    }

    public boolean isNotValid() {
        return StringUtils.isEmpty(getSessionTicket());
    }
}
