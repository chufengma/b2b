package onefengma.demo.server.model.apibeans.login;

import onefengma.demo.server.model.apibeans.AuthSession;

/**
 * Created by chufengma on 16/6/11.
 */
public class ChangePassword extends AuthSession {
    public String oldPassword;
    public String newPassword;
    public String newPasswordConfirm;
}
