package onefengma.demo.server.model.apibeans.product;

import java.util.List;

/**
 * Created by chufengma on 16/7/6.
 */
public class MyCarsResponse extends BasePageResponse {

    public List<CarProductBrief> cars;

    public MyCarsResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
