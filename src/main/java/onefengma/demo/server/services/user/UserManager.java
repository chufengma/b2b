package onefengma.demo.server.services.user;

import onefengma.demo.common.MD5Utils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.model.UploadDemo2;
import onefengma.demo.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.services.apibeans.AuthSession;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.login.Login;
import onefengma.demo.server.services.apibeans.login.Register;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserManager extends BaseManager {

    private UserDataHelper userDataHelper;

    @Override
    public void init() {
        initPages();
        // 注册
        post("register", Register.class, (req, rep, register) -> {
            if (register == null || register.isNotValid()) {
                return error("miss params");
            }

            User user = getUserDataHelper().findUserByName(register.userName);
            if (user != null) {
                return error("用户名已注册!");
            }
            getUserDataHelper().insertUser(register.generateUser());
            return success();
        });

        // 登陆
        post("login", Login.class, (request, response, loginBean) -> {
            if (loginBean == null || loginBean.isNotValid()) {
                return error("miss params");
            }
            User user = getUserDataHelper().findUserByName(loginBean.userName);
            if (user == null) {
                return error("用户名不存在！");
            }
            if (StringUtils.equals(user.getPassword(), MD5Utils.md5(loginBean.password))) {
                AuthHelper.setLoginSession(request, response, user);
                return success();
            } else {
                return error("密码错误！");
            }
        });

        // 用户列表
        get("userList", AuthSession.class, (request, response, requestBean) -> success(getUserDataHelper().getUserList()));

        multiPost("pages/upload", UploadDemo2.class, (request, response, requestBean) -> {
            System.out.println(requestBean.age + "," + requestBean.test + "," + requestBean.myFile);
            return success(requestBean);
        });
    }


    private void initPages() {
        getPage("login", BaseBean.class, "login.html", (request, response, requestBean) -> {
            User user = new User();
            user.setName("AAA");
            user.setPassword("BBB");
            return user;
        });

        getPage("upload", BaseBean.class, "upload.html", (request, response, requestBean) -> {
            return null;
        });
    }


    private UserDataHelper getUserDataHelper() {
        if (userDataHelper == null) {
            userDataHelper = new UserDataHelper();
        }
        return userDataHelper;
    }

    @Override
    public String getParentRoutePath() {
        return "member";
    }

}
