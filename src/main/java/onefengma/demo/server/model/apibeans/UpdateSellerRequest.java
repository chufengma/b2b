package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;

import java.io.File;

/**
 * Created by chufengma on 16/7/9.
 */
public class UpdateSellerRequest extends AuthSession {
    @NotRequired
    public String contact;
    @NotRequired
    public String cantactTel;
    @NotRequired
    public String fax;
    @NotRequired
    public String cityId;
    @NotRequired
    public String officeAddress;
    @NotRequired
    public String qq;
    @NotRequired
    public File cover;
    @NotRequired
    public String shopProfile;
}
