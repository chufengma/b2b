package onefengma.demo.server.model.apibeans.qt;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.model.qt.QtDetail;

import java.util.List;

/**
 * Created by chufengma on 16/8/21.
 */
public class QtListResponse extends BasePageResponse {

    public List<QtDetail> qts;

    public QtListResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
