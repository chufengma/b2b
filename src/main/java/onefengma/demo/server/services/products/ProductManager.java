package onefengma.demo.server.services.products;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.request.PageRoute;
import spark.Request;
import spark.Response;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class ProductManager extends BaseManager {
    @Override
    public void init() {
        page("/api/:apiPath", new PageRoute() {
                    @Override
                    public String handle(Request request, Response response) throws Exception {
                        System.out.println(request.params("apiPath"));
                        return "login.html";
                    }
                }
        );

    }



    @Override
    public String getParentRoutePath() {
        return "";
    }
}
