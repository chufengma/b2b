package onefengma.demo.server.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oreilly.servlet.MultipartRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

import freemarker.template.utility.NullArgumentException;
import onefengma.demo.common.FileHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.request.AuthHelper;
import onefengma.demo.server.core.request.BaseResult;
import onefengma.demo.server.core.request.PageRoute;
import onefengma.demo.server.core.request.ParamsMissException;
import onefengma.demo.server.core.request.TypedRoute;
import onefengma.demo.server.model.apibeans.AdminAuthSession;
import onefengma.demo.server.model.apibeans.AuthData;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.services.user.SellerDataHelper;
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
    public static final int STATUS_NOT_ADMIN = 3;

    public static String parentRoutePath;

    // config
    static {
        Config.instance().init();
        addFilers();
    }

    /*------------------------------ http methods start --------------------------- */
    // wrap http post
    public <T extends BaseBean> void post(String path, Class<T> tClass, TypedRoute<T> route) {
        Spark.post(generatePath(path), doRequest(route, tClass));
    }

    // wrap http get
    public <T extends BaseBean> void get(String path, Class<T> tClass, TypedRoute<T> route) {
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
    public <T extends BaseBean> void multiPost(String path, Class<T> tClass, TypedRoute<T> route) {
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
    private <T extends BaseBean> Route doMultiRequest(TypedRoute route, Class<T> tClass) {
        return (request, response) -> {
            BaseBean requestBean = null;
            try {
                jsonContentType(response);
                requestBean = getPostBean(request, tClass);
                setupAuth(requestBean, request);
                addHeaders(response);
                response.raw().setCharacterEncoding("UTF-8");
                if (!adminSessionCheck(requestBean)) {
                    cleanTmpFiles(requestBean.extra);
                    return error(STATUS_NOT_ADMIN, "not admin", null);
                }
                if (!loginSessionCheck(requestBean)) {
                    cleanTmpFiles(requestBean.extra);
                    return error(STATUS_NOT_LOGIN, "您还没有登录, 请登录后再试", null);
                }
                return route.handle(request, response, requestBean);
            } catch (Exception e) {
                if (requestBean != null) {
                    cleanTmpFiles(requestBean.extra);
                }
                LogUtils.e(e, "error in doMultiRequest:" + e.getMessage());
                return exception(e);
            }
        };
    }

    // really request logic body
    private <T extends BaseBean> Route doRequest(TypedRoute route, Class<T> tClass) {
        return (request, response) -> {
            try {
                jsonContentType(response);
                T reqBean = getRequest(request, tClass);
                addHeaders(response);
                response.raw().setCharacterEncoding("UTF-8");
                if (!adminSessionCheck(reqBean)) {
                    cleanTmpFiles(reqBean.extra);
                    return error(STATUS_NOT_ADMIN, "not admin", null);
                }
                if (!loginSessionCheck(reqBean)) {
                    cleanTmpFiles(reqBean.extra);
                    return error(STATUS_NOT_LOGIN, "not login", null);
                }
                return route.handle(request, response, reqBean);
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

    public static String errorAndClear(BaseBean baseBean, String errorMsg) {
        if (baseBean != null) {
            cleanTmpFiles(baseBean.extra);
        }
        return error(STATUS_ERROR, errorMsg, null);
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

    private <T extends BaseBean> T getPostBean(Request request, Class<T> tClass) throws ParamsMissException, IOException {
        String parentFilePath = FileHelper.getFileFolder();
        File file = new File(parentFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        JSONObject beanJson = new JSONObject();

        ArrayList<File> files = null;
        T requestBean = null;
        try {
            MultipartRequest multipartRequest = new MultipartRequest(request.raw(), FileHelper.getFileFolder(), 10048576, "utf-8", FileHelper.getFileRename());

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
                if (files == null) {
                    files = new ArrayList<>();
                }
                String paramsName = (String) filesEnum.nextElement();
                File value = multipartRequest.getFile(paramsName);
                if (value == null) {
                    continue;
                }
                files.add(value);
                if (value.length() >= 1024*1024*1.5) {
                    value.delete();
                    throw new NullPointerException("上传的图片大小不能超过1.5M");
                }
                FileHelper.compressImage(value.getAbsolutePath());
                beanJson.put(paramsName, value);
            }

            requestBean = beanJson.toJavaObject(tClass);
            requestBean.checkParams(beanJson);
            requestBean.extra = files;

        } catch (Exception e) {
            cleanTmpFiles(files);
            LogUtils.e(e, "error when getPostBean");
            throw e;
        }

        return requestBean;
    }

    public static void cleanTmpFiles(Object extra) {
        if (extra != null && extra instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) extra;
            for(int i = 0; i< arrayList.size() ; i++) {
                if (arrayList.get(i) instanceof File) {
                    ((File)arrayList.get(i)).delete();
                }
            }
        }
    }

    private static <T extends BaseBean> T getRequest(Request request, Class<T> tClass) throws IllegalAccessException, InstantiationException, ParamsMissException, UnsupportedEncodingException {
        T baseBean = null;
        JSONObject beanJson = new JSONObject();
        // parse params
        if (request.requestMethod() == "GET") {
            for (String key : request.queryParams()) {
                beanJson.put(key, StringUtils.urlDecodeStr(request.queryMap(key).value()));
            }
        } else {
            String[] params = request.body().split("&");
            for(String param : params) {
                String[] realParams = param.split("=");
                if (realParams.length != 2) {
                    continue;
                }
                String value = StringUtils.urlDecodeStr(realParams[1]);
                Object keyValue = value;
                try {
                    keyValue = JSON.parseObject(value);
                } catch (Exception e) {
                    try {
                        keyValue = JSON.parseArray(value);
                    } catch (Exception e2) {}
                }
                beanJson.put(realParams[0], keyValue);
            }
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

    private static void setupAuth(BaseBean authSession, Request request) {
        authSession.request = request;
        if (authSession instanceof AuthSession) {
            ((AuthSession) authSession).setAuthData(request);
        }
    }

    private static void addHeaders(Response response) {
        response.header("Access-Control-Allow-Origin", "http://localhost:9090");
        response.header("Access-Control-Allow-Headers", "origin, content-type, accept");
        response.header("Access-Control-Allow-Credentials", "true");
    }

    /*------------------------login handler-----------------------------------*/
    private static boolean loginSessionCheck(Object object) {
        return object instanceof AuthSession ? !((AuthSession) object).isNotValid() : true;
    }

    private static boolean adminSessionCheck(Object object) {
        return object instanceof AdminAuthSession ? !((AdminAuthSession) object).isNotValid() : true;
    }

    private static void gotoPage(String page) {

    }

    private static String becomeUrl = "/html/view/common/become.html";
    private static String loginUrl = "/html/view/common/login.html";

    private static void addFilers() {
        Spark.before((request, response) -> {
                    String pathInfo = request.pathInfo();
                    File pageFile = null;
                    if (pathInfo.startsWith("/admin") || pathInfo.startsWith("/html")) {
                        // other pages
                        if (request.pathInfo().startsWith("/auth")) {
                            pageFile = FileHelper.getFileFromPath("notLogin.html");
                        } else if (request.pathInfo().startsWith("/admin/auth")) {
                            if(!AuthHelper.isAdminLogin(request)) {
                                response.redirect("/admin/login.html");
                            }
                        } else if (pathInfo.startsWith("/html/view/account")) {
                            AuthData serverData = new AuthData(AuthHelper.getServerToken(request), AuthHelper.getServerUserId(request));
                            AuthData clientData = new AuthData(AuthHelper.getRequestToken(request), AuthHelper.getRequestUserId(request));
                            if((serverData == null || !serverData.equals(clientData))) {
                                response.redirect(loginUrl);
                            }
                            if (pathInfo.startsWith("/html/view/account/seller")) {
                                if (!SellerDataHelper.instance().isSeller(clientData.getUserId())) {
                                    response.redirect(becomeUrl);
                                }
                            }
                        } else {
                            return;
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
