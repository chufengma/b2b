package onefengma.demo.server.services.products;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.apibeans.BaseLoginSession;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class ProductManager extends BaseManager {
    @Override
    public void init() {

        get("products", BaseLoginSession.class, (request, response, requestBean) -> {
            return success();
        });

    }
}
