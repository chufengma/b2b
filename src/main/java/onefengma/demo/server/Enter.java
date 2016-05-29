package onefengma.demo.server;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import onefengma.demo.common.FileHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.services.funcs.FuncManager;
import onefengma.demo.server.services.products.ProductManager;
import onefengma.demo.server.services.user.UserManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;

/**
 * @author yfchu
 * @date 2016/4/1
 */
public class Enter {

    private static List<BaseManager> managers = Arrays.asList(
            new UserManager(),
            new ProductManager(),
            new FuncManager()
    );

    public static void main(String[] args) {
        // user modules
        for (BaseManager manager : managers) {
            manager.init();
        }

        // 404 page
        Spark.get("*", (request, response) -> {
            File file = FileHelper.getFileFromPath(request.pathInfo());
            if (file == null) {
                response.redirect(Config.getNotFoundPath());
            }
            return null;
        });
    }

}
