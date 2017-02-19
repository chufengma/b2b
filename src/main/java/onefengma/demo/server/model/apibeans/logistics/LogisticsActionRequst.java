package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.core.BaseNormalAdminBean;
import onefengma.demo.server.core.BaseNormalAdminPageBean;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsActionRequst extends BaseNormalAdminBean {
    public String id;
    public int status; // 0 表示未处理 1表示处理中 2表示已处理
}
