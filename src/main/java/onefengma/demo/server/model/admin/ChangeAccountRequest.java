package onefengma.demo.server.model.admin;

import onefengma.demo.server.model.apibeans.AdminAuthSession;

/**
 * Created by chufengma on 16/7/9.
 */
public class ChangeAccountRequest extends AdminAuthSession {
    public String userId;
    public String oldTel;
    public String newTel;
    public String newPass;
    public String newPassConfirm;
}
