package onefengma.demo.server.services.user;

import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.model.apibeans.login.Login;
import onefengma.demo.server.services.user.SalesDataHelper.SalesManDetail;
import spark.Session;

/**
 * @author yfchu
 * @date 2016/9/1
 */
public class SalesManager extends BaseManager {

    @Override
    public void init() {
        post("login", Login.class, ((request, response, requestBean) -> {
            SalesManDetail salesManDetail = SalesDataHelper.instance().getSalesMan(requestBean.mobile);
            if (salesManDetail == null) {
                return error("没有该专员");
            }
            if (StringUtils.equalsIngcase(IdUtils.md5(requestBean.password), salesManDetail.password)) {
                Session session = request.session();
                session.attribute("sales", salesManDetail.id + "");
                session.maxInactiveInterval(30 * 60 * 24);
                response.cookie("sales", salesManDetail.id + "", 30 * 60 * 24);

                // clean password
                salesManDetail.password = "";
                return success(salesManDetail);
            } else {
                return error("密码不正确");
            }
        }));


    }

    @Override
    public String getParentRoutePath() {
        return "sales";
    }

}
