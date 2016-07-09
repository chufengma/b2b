package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.model.product.IronProductBrief;

import java.util.List;

/**
 * Created by chufengma on 16/7/9.
 */
public class MyIronProductResponse extends BasePageResponse {

    public List<IronProduct> irons;

    public MyIronProductResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
