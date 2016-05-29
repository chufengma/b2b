package onefengma.demo.server.core.request;

import spark.Request;
import spark.Response;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public interface PageRoute {
    String handle(Request request, Response response) throws Exception;
}
