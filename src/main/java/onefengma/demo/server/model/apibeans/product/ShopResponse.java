package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.ShopBrief;

/**
 * Created by chufengma on 16/6/26.
 */
public class ShopResponse extends BasePageResponse {

    public List<ShopBrief> shops;

    public ShopResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
