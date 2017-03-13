package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

import java.io.File;

/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsDriverCertificatesRequest extends BaseBean {
    public String id;

    public File license;
    public File tax;
    public File companyCode;
    public File road;
    public File account;

    public File car1;
    @NotRequired
    public File car2;
    @NotRequired
    public File car3;
}
