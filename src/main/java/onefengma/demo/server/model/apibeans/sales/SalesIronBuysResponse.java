package onefengma.demo.server.model.apibeans.sales;

import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.apibeans.SalesAuthPageBean;
import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.model.product.IronBuyBrief;
import onefengma.demo.server.model.product.SupplyBrief;
import onefengma.demo.server.model.qt.QtDetail;

import java.util.List;

/**
 * Created by chufengma on 16/7/5.
 */
public class SalesIronBuysResponse extends BasePageResponse {
    public List<IronBuyBrief> buys;

    public SalesIronBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
