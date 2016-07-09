package onefengma.demo.server.model.apibeans.login;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.AuthSession;

import java.io.File;

/**
 * Created by chufengma on 16/6/11.
 */
public class ChangeUserProfile extends AuthSession {
    @NotRequired
    public String name;
    @NotRequired
    public File avator;
}
