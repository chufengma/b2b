package onefengma.demo.server.model.apibeans.product;

/**
 * Created by chufengma on 16/7/5.
 */
public class MyHandingBuysResponse extends HandingBuysResponse {

    public int canceledCount;
    public float lossRate;

    public MyHandingBuysResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
