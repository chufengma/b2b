package onefengma.demo.server.core.request;

import java.util.UUID;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.model.User;
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

    public static boolean isAdminLogin(Request request) {
         return (StringUtils.equals(request.cookie("admin"), request.session().attribute("admin"))
                 && StringUtils.equals(request.cookie("role"), request.session().attribute("role"))
                 && StringUtils.equals(request.session().attribute("role"), "0"));
    }

    public static boolean isNormalAdminLogin(Request request) {
        return (StringUtils.equals(request.cookie("admin"), request.session().attribute("admin"))
                && StringUtils.equals(request.cookie("role"), request.session().attribute("role"))
                && Integer.parseInt(request.session().attribute("role")) <= 1);
    }

    public static boolean isSpecOfferAdminLogin(Request request) {
        return (StringUtils.equals(request.cookie("admin"), request.session().attribute("admin"))
                && StringUtils.equals(request.cookie("role"), request.session().attribute("role"))
                && (Integer.parseInt(request.session().attribute("role")) == 2 || Integer.parseInt(request.session().attribute("role")) == 0));
    }

    public static boolean isBuysAdminLogin(Request request) {
        return (StringUtils.equals(request.cookie("admin"), request.session().attribute("admin"))
                && StringUtils.equals(request.cookie("role"), request.session().attribute("role"))
                && (Integer.parseInt(request.session().attribute("role")) == 3 || Integer.parseInt(request.session().attribute("role")) == 0));
    }


    public static boolean isSalesLogin(Request request) {
        return StringUtils.equals(request.cookie("sales"), request.session().attribute("sales"));
    }

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
        session.attribute(USER_ID, user.getUserId());
        session.attribute(TOKEN, token);
        session.maxInactiveInterval(Config.getSessionDieTime());

        response.removeCookie(USER_ID);
        response.removeCookie(TOKEN);

        addCookie(response, USER_ID, user.getUserId());
        addCookie(response, TOKEN, token);
    }

    public static void cleanLoginStatus(Request request, Response response) {
        request.session().removeAttribute(USER_ID);
        request.session().removeAttribute(TOKEN);

        response.removeCookie(USER_ID);
        response.removeCookie(TOKEN);
    }

    public static void addCookie(Response response, String key, String value) {
        response.cookie("/", key, value, -1, false);
    }

}
