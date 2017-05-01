package onefengma.demo.server.model.specoffer;

import onefengma.demo.server.model.apibeans.BasePageBean;
import onefengma.demo.server.model.apibeans.product.BasePageResponse;

import java.util.List;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferFetchResponse extends BasePageResponse {

    public List<SpecOffer> specOffers;

    public SpecOfferFetchResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
