package onefengma.demo.server.model.apibeans.product;

/**
 * Created by chufengma on 16/7/5.
 */
public class MyIronBuysResponse extends IronBuyResponse {

    public int canceledCount;
    public float lossRate;

    public MyIronBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
