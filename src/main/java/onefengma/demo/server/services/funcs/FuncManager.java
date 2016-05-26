package onefengma.demo.server.services.funcs;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.ValidateHelper;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.ValidateCodeBean;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by chufengma on 16/5/26.
 */
public class FuncManager extends BaseManager {

    @Override
    public void init() {
        get("validateCode", BaseBean.class, ((request, response, requestBean1) -> {
            ValidateHelper.generateValidateCode(request, response);
            return "";
        }));

        post("validateCode", ValidateCodeBean.class, ((request, response, requestBean) -> {
            if (ValidateHelper.isValidateCodeOutOfDate(request.session())) {
                return error("验证码过期");
            }
            if (ValidateHelper.isCodeValid(requestBean.code, request.session())) {
                return success("验证成功");
            } else {
                return error("验证失败");
            }
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "validate";
    }
}
