package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.model.product.IronProductBrief;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronsGetResponse extends BasePageResponse {
    public List<IronProductBrief> irons;

    public IronsGetResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
