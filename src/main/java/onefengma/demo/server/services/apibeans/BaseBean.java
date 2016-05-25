package onefengma.demo.server.services.apibeans;

import java.util.HashMap;
import java.util.Map;

import spark.Session;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class BaseBean {
    public Map<String, String> cookies = new HashMap<>();
    public Session session;
}
