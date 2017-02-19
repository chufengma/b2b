package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.model.logistics.LogisticsNormalBean;

import java.util.List;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsPageResponse extends BasePageResponse {

    public List<LogisticsNormalBean> logisticsRequest;

    public LogisticsPageResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }
}
