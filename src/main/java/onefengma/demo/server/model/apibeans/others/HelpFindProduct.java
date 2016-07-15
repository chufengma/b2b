package onefengma.demo.server.model.apibeans.others;

import onefengma.demo.annotation.NotRequired;

/**
 * @author yfchu
 * @date 2016/7/15
 */
public class HelpFindProduct {
    @NotRequired
    public int id;
    public long applyTime;
    public String mobile;
    public String type;
    public String city;
    public String comment;
}
