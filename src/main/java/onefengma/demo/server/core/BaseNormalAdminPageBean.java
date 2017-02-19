package onefengma.demo.server.core;

import onefengma.demo.server.model.apibeans.AdminAuthSession;
import onefengma.demo.server.model.apibeans.NormalAdminAuthSession;

/**
 * Created by chufengma on 16/7/3.
 */
public class BaseNormalAdminPageBean extends NormalAdminAuthSession {
    public int currentPage;
    public int pageCount;
}
