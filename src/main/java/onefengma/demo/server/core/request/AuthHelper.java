package onefengma.demo.server.core.request;

import java.util.UUID;

import onefengma.demo.model.User;
import onefengma.demo.server.config.Config;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class AuthHelper {

    public static final String TOKEN = "token";
    public static final String USER_ID = "userId";

    public static String getServerToken(Request request) {
        if (request == null) {
            return "";
        }
        return request.session().attribute(TOKEN);
    }

    public static String getRequestToken(Request request) {
        if (request == null) {
            return "";
        }
        return request.cookie(TOKEN);
    }

    public static String getServerUserId(Request request) {
        if (request == null) {
            return "";
        }
        return request.session().attribute(USER_ID);
    }

    public static String getRequestUserId(Request request) {
        if (request == null) {
            return "";
        }
        return request.cookie(USER_ID);
    }

    public static void setLoginSession(Request request, Response response, User user) {
        Session session = request.session();
        String token = UUID.randomUUID().toString();
        session.attribute(USER_ID, user.getId());
        session.attribute(TOKEN, token);
        session.maxInactiveInterval(Config.getSessionDieTime());
        response.cookie(USER_ID, user.getId());
        response.cookie(TOKEN, token);
    }

}