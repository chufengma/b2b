package onefengma.demo.server.services.funcs;

import java.io.File;
import java.io.FileInputStream;

import onefengma.demo.common.FileHelper;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.MsgCodeHelper;
import onefengma.demo.server.core.ValidateHelper;
import onefengma.demo.server.services.apibeans.AuthSession;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.codes.MsgCode.MsgCodeResponse;
import onefengma.demo.server.services.apibeans.codes.ValidateCodeBean;
import spark.utils.IOUtils;

/**
 * Created by chufengma on 16/5/26.
 */
public class FuncManager extends BaseManager {

    @Override
    public void init() {
        //  获取验证码
        get("validateCode.png", BaseBean.class, ((request, response, requestBean1) -> {
            ValidateHelper.generateValidateCode(request, response);
            return "";
        }));

        // 验证验证码
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

        //  获取手机号码
        get("msgCode", BaseBean.class, ((request, response, requestBean1) -> {
            MsgCodeHelper.generateMsgCode();
            return success(new MsgCodeResponse("1111"));
        }));

        //  验证手机号码
        post("msgCode", BaseBean.class, ((request1, response1, requestBean1) -> {
            return error("验证码错误");
        }));

        // 上传的文件
        get("files/*", AuthSession.class, ((request, response, requestBean) -> {
            if (request.splat().length == 0) {
                return null;
            }
            String fileName = request.splat()[0];
            File file = new File(Config.getBaseFilePath() + fileName);
            if (file.exists()) {
                response.type(FileHelper.getContentType(fileName));
                IOUtils.copy(new FileInputStream(file.getAbsoluteFile()), response.raw().getOutputStream());
            } else {
                return null;
            }
            return "";
        }));
    }

    @Override
    public String getParentRoutePath() {
        return "";
    }
}
