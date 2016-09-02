package onefengma.demo.server.model.apibeans.login;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.User;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class RegisterMobile extends BaseBean {
    public String password;
    public String mobile;
    public int msgCode;

    public User generateUser() {
        User user = new User();
        user.setPassword(IdUtils.md5(password));
        user.setId(IdUtils.id());
        user.setMobile(mobile);
        return user;
    }
}
