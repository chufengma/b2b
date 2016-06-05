package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.HandingProduct;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingGetResponse extends BasePageResponse {
    public List<HandingProduct> handingProducts;

    public HandingGetResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
