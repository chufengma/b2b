package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;

import onefengma.demo.server.config.Config;
import spark.Request;
import spark.Response;
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
        Spark.post(path, (request, response) -> {
            try {
                jsonContentType(response);
                return route.handle(request, response, getRequest(request, tClass));
            } catch (Exception e) {
                return error(STATUS_ERROR, "innerError", e);
            }
        });
    }

    // wrap http get
    public static void get(String path, Class tClass, TypedRoute route) {
        Spark.get(path, (request, response) -> {
            try {
                jsonContentType(response);
                return route.handle(request, response, getRequest(request, tClass));
            } catch (Exception e) {
                return error(STATUS_ERROR, "innerError", e);
            }
        });
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


    public static <T> T getRequest(Request request, Class<T> tClass) {
        return JSON.parseObject(request.body(), tClass);
    }

    /*-------------------------------abstract methods------------------------------------*/
    public abstract void init();

}
