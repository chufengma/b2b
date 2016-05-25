package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.UUID;

import onefengma.demo.common.MD5Utils;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.services.apibeans.BaseBean;
import onefengma.demo.server.services.apibeans.AuthSession;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.Spark;
import spark.TemplateViewRoute;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public abstract class BaseManager {

    public static final int STATUS_ERROR = 1;
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_NOT_LOGIN = 2;

    public static String parentRoutePath;

    // config
    static {
        Config.instance().init();
    }

    /*------------------------------ http methods start --------------------------- */
    // wrap http post
    public <T> void post(String path, Class<T> tClass, TypedRoute<T> route) {
        Spark.post(generatePath(path), doRequest(route, tClass));

    }

    // wrap http get
    public void get(String path, Class tClass, TypedRoute route) {
        Spark.get(generatePath(path), doRequest(route, tClass));
    }

    // wrap http get pages
    public void getPage(String path, Class tClass, String templeFile, TypedRoute route) {
        Spark.get(generatePath("pages/" + path), doPageRequest(route, tClass, templeFile), Config.instance().getFreeMarkerEngine());
    }



    // get pages
    private TemplateViewRoute doPageRequest(TypedRoute route, Class tClass, String templeFile) {
        return (request, response) -> {
            try {
                Object reqBean = getRequest(request, tClass);
                if (loginSessionCheck(reqBean)) {
                    return new ModelAndView(route.handle(request, response, tClass), templeFile);
                } else {
                    // TODO goto login page
                    return new ModelAndView(null, Config.instance().getNotFoundPath());
                }
            } catch (Exception e) {
                return new ModelAndView(null, Config.instance().getNotFoundPath());
            }
        };
    }

    // really request logic body
    private Route doRequest(TypedRoute route, Class tClass) {
        return (request, response) -> {
            try {
                Object reqBean = getRequest(request, tClass);
                if (loginSessionCheck(reqBean)) {
                    return route.handle(request, response, reqBean);
                } else {
                    return error(STATUS_NOT_LOGIN, "not login", null);
                }
            } catch (Exception e) {
                return error("inner Error", e);
            }
        };
    }

    // generate total path
    private String generatePath(String path) {
        parentRoutePath = getParentRoutePath();
        StringBuilder pathBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(parentRoutePath)) {
            pathBuilder.append(parentRoutePath);
            pathBuilder.append("/");
        }
        pathBuilder.append(path);
        return pathBuilder.toString();
    }

    public File generateFile(String suffix) {
        return new File(Config.getBaseFilePath() + MD5Utils.md5(UUID.randomUUID().toString()) + (StringUtils.isEmpty(suffix) ? "" : ".") + suffix);
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


        T baseBean = null;
        if (request.requestMethod() == "GET") {
            JSONObject beanJson = new JSONObject();
            for (String key : request.queryParams()) {
                beanJson.put(key, request.queryMap(key).value());
            }
            baseBean = beanJson.toJavaObject(tClass);
        } else {
            baseBean = JSON.parseObject(request.body(), tClass);
        }

        if (baseBean == null) {
            baseBean = tClass.newInstance();
        }

        baseBean.cookies.clear();
        baseBean.cookies.putAll(request.cookies());

        if (baseBean instanceof AuthSession) {
            Session session = request.session();
            ((AuthSession) baseBean).setAuth(session.attribute("token"));
        }
        return baseBean;
    }

    /*------------------------login handler-----------------------------------*/
    private static boolean loginSessionCheck(Object object) {
        return object instanceof AuthSession ? !((AuthSession) object).isNotValid() : true;
    }

    /*-------------------------------abstract methods------------------------------------*/
    public abstract void init();

    public abstract String getParentRoutePath();

}
