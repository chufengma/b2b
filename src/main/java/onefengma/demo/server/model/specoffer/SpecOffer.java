package onefengma.demo.server.model.specoffer;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOffer {
    @NotRequired
    public String id;
    public String offerId;
    public String type;
    public String material;
    public String surface;
    public String proPlace;
    public String spec;
    public String tolerance;
    public String price;
    public String unit;
    public String title;
    public String des;
    public String cover;
    public String pic1 = "";
    public String pic2 = "";
    public String pic3 = "";
    public String pic4 = "";
    public String pic5 = "";
    public String tel;
    public String hostName;
    public String hostTel;
    public String province;
    public String city;
    public String cityDesc;
    public String priceDesc1;
    public String priceDesc2;
    public String count;
    public String pushTime = "";
    public String updateTime = "";
    public int status = 0;
}
