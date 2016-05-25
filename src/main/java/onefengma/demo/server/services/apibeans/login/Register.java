package onefengma.demo.server.services.apibeans.login;

import java.util.UUID;

import onefengma.demo.common.MD5Utils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.model.User;
import onefengma.demo.server.services.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Register extends BaseBean {
    public String userName;
    public String password;
    public String email;

    public boolean isNotValid() {
        return StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email);
    }

    public User generateUser() {
        User user = new User();
        user.setName(userName);
        user.setPassword(MD5Utils.md5(password));
        user.setId(UUID.randomUUID().toString());
        return user;
    }
}
