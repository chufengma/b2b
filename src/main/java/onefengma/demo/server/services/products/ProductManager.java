package onefengma.demo.server.services.products;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.apibeans.AuthSession;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class ProductManager extends BaseManager {
    @Override
    public void init() {

        get("products", AuthSession.class, (request, response, requestBean) -> {
            return success();
        });

    }

    @Override
    public String getParentRoutePath() {
        return "products";
    }
}
