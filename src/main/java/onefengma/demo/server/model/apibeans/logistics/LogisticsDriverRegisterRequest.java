package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsDriverRegisterRequest extends BaseBean {
    public String mobile;
    public int code;
    public String password;
}
