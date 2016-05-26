package onefengma.demo.server.services.apibeans;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.request.ParamsMissException;
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

    public boolean checkParams(JSONObject jsonObject) throws ParamsMissException {
        for(String key : getRequiredParams()) {
            if (!jsonObject.containsKey(key)) {
                throw new ParamsMissException(key) ;
            }
        }
        return true;
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
