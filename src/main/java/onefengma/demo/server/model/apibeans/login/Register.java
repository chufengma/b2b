package onefengma.demo.server.model.apibeans.login;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.model.User;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Register extends BaseBean {
    @NotRequired
    public String userName;
    public String validateCode;
    public String password;
    public String passwordConfirm;
    public String mobile;
    public String msgCode;

    public User generateUser() {
        User user = new User();
        user.setName(userName);
        user.setPassword(IdUtils.md5(password));
        user.setId(IdUtils.id());
        user.setMobile(mobile);
        return user;
    }

    public boolean isPasswordComfirmed() {
        return StringUtils.equals(password, passwordConfirm);
    }

    public boolean isPasswordRight() {
        return password.length() > 6 && password.length() <= 16;
    }
}
