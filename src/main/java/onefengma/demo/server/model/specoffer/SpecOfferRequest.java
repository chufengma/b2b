package onefengma.demo.server.model.specoffer;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

import java.io.File;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferRequest extends BaseBean {
    public String type;
    public String material;
    public String surface;
    public String proPlace;
    public String spec;
    public String price;
    public String unit;
    public String title;
    public File cover;
    public String tel;
    public String hostName;
    public String hostTel;
    public String city;
    public String province;
    public String priceDesc1;
    public String priceDesc2;
    public String count;

    @NotRequired
    public String tolerance;
    @NotRequired
    public String cityDesc;
    @NotRequired
    public String des;
    @NotRequired
    public File pic1;
    @NotRequired
    public File pic2;
    @NotRequired
    public File pic3;
    @NotRequired
    public File pic4;
    @NotRequired
    public File pic5;
}
