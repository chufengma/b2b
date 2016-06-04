package onefengma.demo.server.model.apibeans.product;

import java.util.List;

import onefengma.demo.server.model.product.IronProduct;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronsResponse {
    public int maxCount;
    public int currentPage;
    public int pageCount;
    public List<IronProduct> irons;
}
