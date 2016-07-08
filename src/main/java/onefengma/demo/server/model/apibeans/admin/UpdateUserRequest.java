package onefengma.demo.server.model.apibeans.admin;

import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class UpdateUserRequest extends AdminAuthSession {
    public String userId;
    public int score;
    public int salesmanId;
}
