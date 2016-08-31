package onefengma.demo.server.model;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * @author yfchu
 * @date 2016/8/31
 */
public class IronSubscribePushResponse extends AuthSession {
    @NotRequired
    public String types;
    @NotRequired
    public String surfaces;
    @NotRequired
    public String materials;
    @NotRequired
    public String proPlaces;
}
