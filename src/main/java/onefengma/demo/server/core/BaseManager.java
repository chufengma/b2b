package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;

import onefengma.demo.server.config.Config;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.BaseLoginSession;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public abstract class BaseManager {

    public static final int STATUS_ERROR = 1;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_NOT_LOGIN = 2;

    // config
    static {
        Config.instance().init();
    }

    /*------------------------------ http methods start --------------------------- */
    // wrap http post
    public static <T> void post(String path, Class<T> tClass, TypedRoute<T> route) {
        Spark.post(path, doRequest(route, tClass));
    }

    // wrap http get
    public static void get(String path, Class tClass, TypedRoute route) {
        Spark.get(path, doRequest(route, tClass));
    }

    // really request logic body
    private static Route doRequest(TypedRoute route, Class tClass) {
        return (request, response) -> {
            try {
                jsonContentType(response);
                Object reqBean = getRequest(request, tClass);
                if (loginSessionCheck(reqBean)) {
                    return route.handle(request, response, reqBean);
                } else {
                    return error(STATUS_NOT_LOGIN, "not login", null);
                }
            } catch (Exception e) {
                return error(STATUS_ERROR, "innerError", e);
            }
        };
    }

    // most content type is JSON
    private static void jsonContentType(Response response) {
        response.type("application/json;charset=utf-8");
    }

    /*------------------------------ output start ---------------------------*/
    public static String success(Object data) {
        return toJson(new BaseResult(STATUS_SUCCESS, "", data));
    }

    public static String success() {
        return toJson(new BaseResult(STATUS_SUCCESS, "", "{}"));
    }

    public static String error(int code, String errorMsg, Throwable exception) {
        StringBuffer stringBuffer = new StringBuffer();
        if (exception != null) {
            stringBuffer.append(exception.toString());
            StackTraceElement[] trace = exception.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                stringBuffer.append("" + traceElement);
                stringBuffer.append("\\r\\n");
            }
        }
        return toJson(new BaseResult(code, errorMsg, stringBuffer.toString()));
    }

    public static String error() {
        return error(STATUS_ERROR, "interal error", null);
    }

    public static String error(String errorMsg, Throwable exception) {
        return error(STATUS_ERROR, errorMsg, exception);
    }

    public static String error(String errorMsg) {
        return error(STATUS_ERROR, errorMsg, null);
    }

    // most of result is Json
    public static String toJson(BaseResult result) {
        String jsonResult;
        try {
            jsonResult = JSON.toJSONString(result);
        } catch (Exception e) {
            jsonResult = JSON.toJSONString(new BaseResult(STATUS_ERROR, "server Error", null));
        }
        LogUtils.i("output : " + jsonResult);
        return jsonResult;
    }


    public static <T extends BaseBean> T getRequest(Request request, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T baseBean = JSON.parseObject(request.body(), tClass);
        if (baseBean == null) {
            baseBean = tClass.newInstance();
        }
        baseBean.session.clear();
        baseBean.session.putAll(request.cookies());

        if (baseBean instanceof BaseLoginSession) {
            ((BaseLoginSession) baseBean).setAuth(request.headers(BaseLoginSession.HEADER_TICKET));
        }
        return baseBean;
    }

    /*------------------------login handler-----------------------------------*/
    private static boolean loginSessionCheck(Object object) {
        return object instanceof BaseLoginSession ? !((BaseLoginSession) object).isNotValid() : true;
    }

    /*-------------------------------abstract methods------------------------------------*/
    public abstract void init();

}
