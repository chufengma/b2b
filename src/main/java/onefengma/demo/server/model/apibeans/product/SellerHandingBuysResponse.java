package onefengma.demo.server.model.apibeans.product;

/**
 * Created by chufengma on 16/7/9.
 */
public class SellerHandingBuysResponse extends HandingBuysResponse {

    public int offerTimes;
    public float offerWinRate;

    public SellerHandingBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
