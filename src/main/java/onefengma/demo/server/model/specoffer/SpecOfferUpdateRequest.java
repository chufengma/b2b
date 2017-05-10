package onefengma.demo.server.model.specoffer;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

import java.io.File;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferUpdateRequest extends BaseBean {
    @NotRequired
    public String spec;
    @NotRequired
    public String tolerance = "";
    @NotRequired
    public String price;
    @NotRequired
    public String unit;
    @NotRequired
    public String title;
    @NotRequired
    public String tel;
    @NotRequired
    public String hostName;
    @NotRequired
    public String hostTel;
    @NotRequired
    public String city;
    @NotRequired
    public String province;
    @NotRequired
    public String priceDesc1;
    @NotRequired
    public String priceDesc2;
    @NotRequired
    public String count;
    @NotRequired
    public String cityDesc = "";
    @NotRequired
    public String des;

    public String offerId;
}
