package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.IronProduct;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronsGetResponse extends BasePageResponse {
    public List<IronProduct> irons;

    public IronsGetResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
