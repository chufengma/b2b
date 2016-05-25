package onefengma.demo.server.services.user;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

import javax.servlet.MultipartConfigElement;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.MD5Utils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.model.User;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.BaseLoginSession;
import onefengma.demo.server.services.apibeans.login.Login;
import onefengma.demo.server.services.apibeans.login.Register;
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
                return success();
            } else {
                return error("密码错误！");
            }
        });

        // 用户列表
        get("userList", BaseLoginSession.class, (request, response, requestBean) -> success(getUserDataHelper().getUserList()));


        Spark.post("/member/pages/upload", (request, response) -> {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            File file = generateFile(FileHelper.getFileSuffix(FileHelper.getFileName(request.raw().getPart("uploaded_file"))));
            try (InputStream is = request.raw().getPart("uploaded_file").getInputStream()) {
                FileUtils.copyInputStreamToFile(is, file);
            }
            return "File uploaded";
        });
    }


    private void initPages() {
        getPage("login", BaseBean.class, "login.html", (request, response, requestBean) -> {
            User user = new User();
            user.setName("AAA");
            user.setPassword("BBB");
            return null;
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
