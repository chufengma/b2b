package onefengma.demo.server.services.user;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.DateHelper.TimeRange;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.admin.AdminUsersRequest;
import onefengma.demo.server.model.admin.AdminUsersResponse;
import onefengma.demo.server.model.apibeans.AdminAuthSession;
import onefengma.demo.server.model.apibeans.admin.AdminLoginRequest;
import onefengma.demo.server.model.apibeans.admin.DeleteUserRequest;
import onefengma.demo.server.model.apibeans.admin.UpdateUserRequest;
import onefengma.demo.server.services.user.AdminDataManager.BuyerBrief;
import spark.Session;

import java.util.List;

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
                Session session = request.session();
                session.attribute("admin", admin.id + "");
                session.maxInactiveInterval(30 * 60);
                response.cookie("admin", admin.id + "", 30 * 60);
                return success("登陆成功");
            } else {
                return error("密码不正确");
            }
        }));

        get("users", AdminUsersRequest.class, ((request, response, requestBean) -> {
            if (requestBean.time == 0) {
                requestBean.time = System.currentTimeMillis();
            }
            TimeRange timeRange = DateHelper.getMonthTimeRange(requestBean.time);
            AdminUsersResponse adminUsersResponse = AdminDataManager.instance()
                    .getBuyer(new PageBuilder(requestBean.currentPage, requestBean.pageCount)
                            .setTime(timeRange.startTime, timeRange.endTime)
                            .addEqualWhere("mobile", requestBean.userTel)
                            .addEqualWhere("tel", requestBean.salesTel));
            adminUsersResponse.currentPage = requestBean.currentPage;
            adminUsersResponse.pageCount = requestBean.pageCount;
            return success(adminUsersResponse);
        }));

        post("deleteUser", DeleteUserRequest.class, ((request, response, requestBean) -> {
            AdminDataManager.instance().deleteUser(requestBean.userId);
            return success("删除成功");
        }));

        post("updateUser", UpdateUserRequest.class, ((request, response, requestBean) -> {
            if (!UserDataHelper.instance().isSalesManExited(requestBean.salesmanId)) {
                return error("该顾问不存在");
            }
            AdminDataManager.instance().updateUser(requestBean.userId, requestBean.salesmanId);
            return success("修改成功");
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "admin";
    }
}
