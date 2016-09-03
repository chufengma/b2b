package onefengma.demo.server.model.apibeans.sales;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.services.user.SalesDataHelper;

import java.util.List;

/**
 * Created by chufengma on 16/9/2.
 */
public class SalesManSellerResponse extends BasePageResponse {

    public List<SalesDataHelper.SellerInfo> sellerInfos;

    public SalesManSellerResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
