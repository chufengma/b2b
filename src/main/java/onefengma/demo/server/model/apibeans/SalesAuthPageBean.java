package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 16/7/3.
 */
public class SalesAuthPageBean extends SalesAuthSession {
    public int currentPage;
    public int pageCount;
    @NotRequired
    public int status = -1;
}
