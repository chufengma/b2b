package onefengma.demo.server.services.apibeans.login;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.IdUtils;
import onefengma.demo.model.User;
import onefengma.demo.server.services.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Register extends BaseBean {
    @NotRequired
    public String userName;
    public String password;
    public String mobile;

    public User generateUser() {
        User user = new User();
        user.setName(userName);
        user.setPassword(IdUtils.md5(password));
        user.setId(IdUtils.id());
        user.setMobile(mobile);
        return user;
    }
}
