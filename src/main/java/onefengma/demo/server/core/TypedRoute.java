package onefengma.demo.server.core;

import spark.Request;
import spark.Response;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public interface TypedRoute<T> {
    Object handle(Request request, Response response, T requestBean) throws Exception;
}
