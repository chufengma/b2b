package onefengma.demo.server.core;

import onefengma.demo.common.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chufengma on 16/7/9.
 */
public class UpdateBuilder {

    Map<String, String> stringMap = new HashMap<>();

    public void addStringMap(String key, String value) {
        stringMap.put(key, value);
    }

    public String generateSql() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : stringMap.keySet()) {
            String value = stringMap.get(key);
            if (!StringUtils.isEmpty(value)) {
                stringBuilder.append(key + "='" + value + "',");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

}
