package onefengma.demo.server.services.user;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.apibeans.admin.AdminLoginRequest;
import spark.Session;

/**
 * Created by chufengma on 16/7/2.
 */
public class AdminManager extends BaseManager {

    @Override
    public void init() {
        post("login", AdminLoginRequest.class, ((request, response, requestBean) -> {
            Admin admin = AdminDataManager.instance().getAdmin(requestBean.userName);
            if (admin == null) {
                return error("没有该管理员");
            }
            if (StringUtils.equalsIngcase(IdUtils.md5(requestBean.password), admin.password)) {
                Session session  = request.session();
                session.attribute("admin", admin.id + "");
                session.maxInactiveInterval(30 * 60);
                response.cookie("admin", admin.id + "", 30 * 60);
                return success("登陆成功");
            } else {
                return error("密码不正确");
            }
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "admin";
    }
}
