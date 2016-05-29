package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oreilly.servlet.MultipartRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.request.BaseResult;
import onefengma.demo.server.core.request.PageRoute;
import onefengma.demo.server.core.request.ParamsMissException;
import onefengma.demo.server.core.request.TypedRoute;
import onefengma.demo.server.services.apibeans.AuthSession;
import onefengma.demo.server.services.apibeans.BaseBean;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.utils.IOUtils;

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
        addFilers();
    }

    /*------------------------------ http methods start --------------------------- */
    // wrap http post
    public <T> void post(String path, Class<T> tClass, TypedRoute<T> route) {
        Spark.post(generatePath(path), doMultiRequest(route, tClass));
    }

    // wrap http get
    public void get(String path, Class tClass, TypedRoute route) {
        Spark.get(generatePath(path), doRequest(route, tClass));
    }

    // wrap http get pages
    public void getPage(String path, Class tClass, String templeFile, TypedRoute route) {
        Spark.get(generatePath(path), doPageRequest(route, tClass, templeFile), Config.instance().getFreeMarkerEngine());
    }

    // wrap http get pages
    public void page(String path, PageRoute route) {
        Spark.get(generatePath(path), (request, response) -> {
            return new ModelAndView(null, route.handle(request, response));
        }, Config.instance().getFreeMarkerEngine());
    }

    // wrap http multipost pages
    public <T> void multiPost(String path, Class<T> tClass, TypedRoute<T> route) {
        Spark.post(generatePath(path), doMultiRequest(route, tClass));
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
    private Route doMultiRequest(TypedRoute route, Class tClass) {
        return (request, response) -> {
            try {
                Object requestBean = getMultiBean(request, tClass);
                jsonContentType(response);
                setupAuth(request, request);
                if (loginSessionCheck(requestBean)) {
                    return route.handle(request, response, requestBean);
                } else {
                    return error(STATUS_NOT_LOGIN, "not login", null);
                }
            } catch (Exception e) {
                return exception(e);
            }
        };
    }

    // really request logic body
    private Route doRequest(TypedRoute route, Class tClass) {
        return (request, response) -> {
            try {
                Object reqBean = getRequest(request, tClass);
                jsonContentType(response);
                if (loginSessionCheck(reqBean)) {
                    return route.handle(request, response, reqBean);
                } else {
                    return error(STATUS_NOT_LOGIN, "not login", null);
                }
            } catch (Exception e) {
                return exception(e);
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

    // most content type is JSON
    private static void jsonContentType(Response response) {
        response.type("application/json;charset=utf-8");
    }

    private String exception(Exception exception) {
        return error(exception.getMessage(), exception);
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

    private <T extends BaseBean> T getMultiBean(Request request, Class<T> tClass) throws IOException, IllegalAccessException, ParamsMissException {
        String parentFilePath = FileHelper.getFileFolder();
        File file = new File(parentFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        MultipartRequest multipartRequest = new MultipartRequest(request.raw(), FileHelper.getFileFolder(), 10048576, "utf-8", FileHelper.getFileRename());
        JSONObject beanJson = new JSONObject();

        // params
        Enumeration paramsEnum = multipartRequest.getParameterNames();
        while (paramsEnum.hasMoreElements()) {
            String paramsName = (String) paramsEnum.nextElement();
            String value = multipartRequest.getParameter(paramsName);
            beanJson.put(paramsName, value);
        }

        // files
        Enumeration filesEnum = multipartRequest.getFileNames();
        while (filesEnum.hasMoreElements()) {
            String paramsName = (String) filesEnum.nextElement();
            File value = multipartRequest.getFile(paramsName);
            beanJson.put(paramsName, value);
        }

        T requestBean = beanJson.toJavaObject(tClass);
        requestBean.checkParams(beanJson);

        return requestBean;
    }

    private static <T extends BaseBean> T getRequest(Request request, Class<T> tClass) throws IllegalAccessException, InstantiationException, ParamsMissException {
        T baseBean = null;
        JSONObject beanJson;
        // parse params
        if (request.requestMethod() == "GET") {
            beanJson = new JSONObject();
            for (String key : request.queryParams()) {
                beanJson.put(key, request.queryMap(key).value());
            }
        } else {
            beanJson = JSON.parseObject(request.body());
        }

        if (beanJson == null) {
            beanJson = new JSONObject();
        }

        baseBean = beanJson.toJavaObject(tClass);
        if (baseBean == null) {
            baseBean = tClass.newInstance();
        }
        // check params
        baseBean.checkParams(beanJson);

        setupAuth(baseBean, request);

        return baseBean;
    }

    private static void setupAuth(Object authSession, Request request) {
        if (authSession instanceof AuthSession) {
            ((AuthSession) authSession).setAuthData(request);
        }
    }

    /*------------------------login handler-----------------------------------*/
    private static boolean loginSessionCheck(Object object) {
        return object instanceof AuthSession ? !((AuthSession) object).isNotValid() : true;
    }

    private static void gotoPage(String page) {

    }

    private static void addFilers() {
        Spark.before((request, response) -> {
                    String pathInfo = request.pathInfo();
                    File pageFile = null;
                    if (pathInfo.endsWith("/")) {
                        // goto main page
                        pageFile = FileHelper.getFileFromPath(Config.getIndexPath());
                    } else if (request.pathInfo().endsWith(".html")) {
                        // other pages
                        if (request.pathInfo().startsWith("/auth")) {
                            pageFile = FileHelper.getFileFromPath("notLogin.html");
                        } else {
                            pageFile = FileHelper.getFileFromPath(pathInfo);
                        }
                    }
                    // other request
                    if (pageFile == null) {
                        return;
                    }
                    outputFile(pageFile, response);
                    Spark.halt(200);
                }
        );

    }

    public static void outputFile(File file, Response response) throws IOException {
        // output
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.raw().setCharacterEncoding("utf-8");
            IOUtils.copy(inputStream, response.raw().getOutputStream());
        }
    }

    /*-------------------------------abstract methods------------------------------------*/
    public abstract void init();

    public abstract String getParentRoutePath();

}
