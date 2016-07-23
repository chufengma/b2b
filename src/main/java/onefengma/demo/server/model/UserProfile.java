package onefengma.demo.server.model;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.services.user.UserDataHelper.UserInfo;

/**
 * Created by chufengma on 16/7/10.
 */
public class UserProfile {
    public String userId;
    public String name;
    public String avator;
    public String mobile;
    public int becomeStatus = 0;

    @NotRequired
    public UserInfo userData;
    @NotRequired
    public UserInfo sellerData;
    @NotRequired
    public SalesMan salesMan;
}
