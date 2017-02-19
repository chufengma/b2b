package onefengma.demo.server.model.logistics;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsNormalBean {
    @NotRequired
    public int id;
    public String startPoint;
    public String endPoint;
    public String tel;
    public String goods;
    public String specCommand;
    public String comment;
    public int status = 0;
    public long pushTime;
}
