package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseNormalAdminPageBean;
import onefengma.demo.server.model.apibeans.BasePageBean;
import onefengma.demo.server.model.apibeans.product.BasePageResponse;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsPageRequst extends BaseNormalAdminPageBean {
    @NotRequired
    public int status = -1; //  0 表示未处理 1表示处理中 2表示已处理
}
