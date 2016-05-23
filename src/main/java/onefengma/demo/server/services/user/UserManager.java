package onefengma.demo.server.services.user;

import java.util.UUID;

import onefengma.demo.model.SimpleStr;
import onefengma.demo.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.apibeans.Register;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserManager extends BaseManager {

    @Override
    public void init() {
        post("register", (req, rep) -> {
            Register request = getRequest(req, Register.class);
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName("fengma");
            user.setPassword("86868239");
            new UserDataHelper().insertUser(user);
            return success(new SimpleStr("好吧，这是嘴刁的"));
        });
    }


}
