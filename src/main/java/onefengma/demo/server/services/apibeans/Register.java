package onefengma.demo.server.services.apibeans;

import onefengma.demo.common.StringUtils;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Register {
    public String userName;
    public String password;
    public String email;

    public boolean isNotValid() {
        return StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email);
    }
}
