package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsRequest extends BaseBean {
    public String startPoint;
    public String endPoint;
    public String tel;

    public String goods1;
    public String goods1Count;
    @NotRequired
    public String goods2;
    @NotRequired
    public String goods2Count;

    @NotRequired
    public String goods3;
    @NotRequired
    public String goods3Count;

    @NotRequired
    public String goods4;
    @NotRequired
    public String goods4Count;

    @NotRequired
    public String sepcCommand = "";
    @NotRequired
    public String comment = "";
}
