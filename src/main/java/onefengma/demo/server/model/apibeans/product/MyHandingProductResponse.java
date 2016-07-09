package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.HandingProduct;
import onefengma.demo.server.model.product.IronProduct;

import java.util.List;

/**
 * Created by chufengma on 16/7/9.
 */
public class MyHandingProductResponse extends BasePageResponse {

    public List<HandingProduct> handings;

    public MyHandingProductResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
