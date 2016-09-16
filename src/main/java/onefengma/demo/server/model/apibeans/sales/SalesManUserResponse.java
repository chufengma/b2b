package onefengma.demo.server.model.apibeans.sales;

import onefengma.demo.server.model.apibeans.product.BasePageResponse;
import onefengma.demo.server.services.user.SalesDataHelper;

import java.util.List;

/**
 * Created by chufengma on 16/9/2.
 */
public class SalesManUserResponse extends BasePageResponse {

    public List<SalesDataHelper.UserInfo> userInfos;

    public SalesManUserResponse(int currentPage, int pageCount) {
        super(currentPage, pageCount);
    }

}
