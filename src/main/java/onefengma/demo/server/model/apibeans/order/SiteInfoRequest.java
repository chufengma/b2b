package onefengma.demo.server.model.apibeans.order;

import onefengma.demo.server.model.apibeans.AdminAuthSession;
import onefengma.demo.server.model.apibeans.BaseAuthPageBean;

/**
 * Created by chufengma on 16/7/3.
 */
public class SiteInfoRequest extends AdminAuthSession {
    public long startTime;
    public long endTime;
    public int type;
}
