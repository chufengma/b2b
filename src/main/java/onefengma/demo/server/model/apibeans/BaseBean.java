package onefengma.demo.server.model.apibeans;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.request.ParamsMissException;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class BaseBean {
    @NotRequired
    private static Set<String> requiredParams;
    @NotRequired
    public Map<String, String> cookies = new HashMap<>();
    @NotRequired
    public Session session;
    @NotRequired
    public Object extra;
    @NotRequired
    public Request request;
    @NotRequired
    public Response response;
    @NotRequired
    public int appFlag = -1;

    public boolean checkParams(JSONObject jsonObject) throws ParamsMissException {
        for(String key : getRequiredParams()) {
            if (!jsonObject.containsKey(key)) {
                throw new ParamsMissException(key) ;
            }
        }
        return true;
    }

    public boolean isMobile() {
        return !StringUtils.isEmpty(request.headers("X-Mobile-Flag"));
    }

    public int getMobileFlag() {
         if (!StringUtils.isEmpty(request.headers("X-Mobile-Flag"))) {
             return StringUtils.equals(request.headers("X-Mobile-Flag"), "Android") ? 1 : 2;
         } else {
             return 0;
         }
    }

    public Set<String> getRequiredParams() {
        if (requiredParams != null) {
            return requiredParams;
        }
        Set<String> requiredParams = new TreeSet<>();
        for(Class<?> clazz = this.getClass(); clazz != Object.class ; clazz = clazz.getSuperclass()) {
            Field[] fileds =  clazz.getDeclaredFields();
            for(Field field : fileds) {
                if (field.isAnnotationPresent(NotRequired.class)) {
                    continue;
                }
                requiredParams.add(field.getName());
            }
        }
        return requiredParams;
    }
}
