package onefengma.demo.server.core.request;

import spark.Request;
import spark.Response;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public interface TempleRoute<T> {
    Object handle(Request request, Response response, T requestBean) throws Exception;
}
