package onefengma.demo.server.services.user;

import java.util.HashMap;
import java.util.Map;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ValidateCode;
import onefengma.demo.server.model.UploadDemo;
import onefengma.demo.server.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.MsgCodeHelper;
import onefengma.demo.server.core.ValidateHelper;
import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.login.Login;
import onefengma.demo.server.model.apibeans.login.Register;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserManager extends BaseManager {

    private UserDataHelper userDataHelper;

    @Override
    public void init() {
        initPages();
        /* 注册 */
        post("register", Register.class, (req, rep, register) -> {
            // 输入验证
            if (!register.isPasswordComfirmed()) {
                return error("俩次密码输入不一致");
            }
            if (!register.isPasswordRight()) {
                return error("密码长度为 6~16");
            }
            if(!ValidateHelper.isCodeValid(register.validateCode, req.session())) {
                return error("验证码不正确");
            }
            if (!MsgCodeHelper.isMsgCodeRight(register.msgCode, register.mobile)) {
                return error("短信验证码不正确");
            }

            // 是否是重复用户
            User user = getUserDataHelper().findUserByMobile(register.mobile);
            if (user != null) {
                return error("用户名已注册!");
            }

            getUserDataHelper().insertUser(register.generateUser());
            return success();
        });

        // 登陆
        post("login", Login.class, (request, response, loginBean) -> {
            User user = getUserDataHelper().findUserByMobile(loginBean.mobile);
            if (user == null) {
                return error("用户名不存在！");
            }
            if (StringUtils.equals(user.getPassword(), IdUtils.md5(loginBean.password))) {
                AuthHelper.setLoginSession(request, response, user);
                return success();
            } else {
                return error("密码错误！");
            }
        });

        // 用户列表
        get("userList", AuthSession.class, (request, response, requestBean) -> success(getUserDataHelper().getUserList()));

        multiPost("upload", UploadDemo.class, (request, response, requestBean) -> {
            return success(requestBean);
        });

    }


    private void initPages() {
        getPage("login", BaseBean.class, "login.html", (request, response, requestBean) -> {
            ValidateCode validateCode = ValidateCode.getDefaultValidateCode(request.session());
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", "Fengma");
            params.put("password", "123456");
            return params;
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
