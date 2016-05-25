package onefengma.demo.server.services.apibeans.login;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.services.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class Login extends BaseBean {
    public String userName;
    public String password;

    public boolean isNotValid() {
        return StringUtils.isEmpty(userName) || StringUtils.isEmpty(password);
    }
}
