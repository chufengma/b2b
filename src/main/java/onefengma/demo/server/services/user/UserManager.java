package onefengma.demo.server.services.user;

import java.util.UUID;

import onefengma.demo.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.apibeans.Register;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserManager extends BaseManager {

    private UserDataHelper userDataHelper;

    @Override
    public void init() {
        post("register", Register.class, (req, rep, register) -> {
            if (register == null || register.isNotValid()) {
                return error("miss params");
            }
            User user = new User();
            user.setName(register.userName);
            user.setPassword(register.password);
            user.setId(UUID.randomUUID().toString());
            insertUser(user);
            return success();
        });

        get("userList", Void.class, (request, response, requestBean) -> success(getUserDataHelper().getUserList()));
    }


    private UserDataHelper getUserDataHelper() {
        if (userDataHelper == null) {
            userDataHelper = new UserDataHelper();
        }
        return userDataHelper;
    }

    private void insertUser(User user) {
        getUserDataHelper().insertUser(user);
    }

}
