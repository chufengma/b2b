package onefengma.demo.server.services.apibeans;

import onefengma.demo.common.StringUtils;

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
