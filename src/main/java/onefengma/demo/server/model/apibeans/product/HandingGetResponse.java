package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.HandingProduct;
import onefengma.demo.server.model.product.HandingProductBrief;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingGetResponse extends BasePageResponse {
    public List<HandingProductBrief> handingProducts;

    public HandingGetResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
