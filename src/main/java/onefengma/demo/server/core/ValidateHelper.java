package onefengma.demo.server.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import onefengma.demo.common.StringUtils;
import onefengma.demo.common.ValidateCode;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * Created by chufengma on 16/5/26.
 */
public class ValidateHelper {

    private static final String VALIDATE_CODE_GE_TIME = "validate_code_ge_time";
    private static final String VALIDATE_CODE = "validate_code";

    private static final long DEFAULT_VALIDATE_TIME = 30 * 1000;

    public static ValidateCode generateValidateCode(Request request, Response response) throws IOException {
        ValidateCode validateCode = ValidateCode.getDefaultValidateCode(request.session());
        response.type("image/png");
        validateCode.write(response.raw().getOutputStream());

        response.cookie("JSESSIONID", request.session().id());
        request.session().attribute(VALIDATE_CODE, validateCode.getCode());
        request.session().attribute(VALIDATE_CODE_GE_TIME, System.currentTimeMillis());
        return validateCode;
    }

    public static boolean isValidateCodeOutOfDate(Session session) {
        long savedTime = session.attribute(VALIDATE_CODE_GE_TIME) == null ? 0 : session.attribute(VALIDATE_CODE_GE_TIME);
        return (System.currentTimeMillis() - savedTime > DEFAULT_VALIDATE_TIME);
    }

    public static boolean isCodeValid(String clientCode, Session session) {
        return StringUtils.equalsIngcase(clientCode, session.attribute(VALIDATE_CODE));
    }

}
