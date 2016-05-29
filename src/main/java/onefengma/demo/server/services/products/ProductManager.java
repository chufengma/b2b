package onefengma.demo.server.services.products;

import org.eclipse.jetty.http.MimeTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.activation.MimeType;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.request.PageRoute;
import onefengma.demo.server.services.apibeans.AuthSession;
import onefengma.demo.server.services.apibeans.BaseBean;
import spark.Filter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.ResponseTransformerRouteImpl;
import spark.Route;
import spark.Spark;
import spark.utils.IOUtils;

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
