package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * Created by chufengma on 16/6/4.
 */
public class IronsRequest extends BaseBean {
    public int currentPage;
    public int pageCount;

    @NotRequired
    public String material;
    @NotRequired
    public String surface;
    @NotRequired
    public String proPlace;
    @NotRequired
    public String ironType;
}
