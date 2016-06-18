package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.HandingProduct;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.model.product.Shop;
import onefengma.demo.server.model.product.ShopBrief;

/**
 * Created by chufengma on 16/6/11.
 */
public class SearchResponse extends BasePageResponse {

    public int type;
    public List<IronProduct> ironProducts;
    public List<HandingProduct> handingProducts;
    public List<ShopBrief> shops;

    public SearchResponse(int type, int currentPage, int pageCount) {
        super(currentPage, pageCount);
        this.type = type;
    }
}
