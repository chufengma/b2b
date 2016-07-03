package onefengma.demo.server.model.product;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 16/7/3.
 */
public class IronDetail {
    public String id;
    public String userId;
    public String proId;
    public String surface;
    public String ironType;
    public String proPlace;
    public String material;
    public String sourceCityId;
    public String title;
    public float price;
    public String cover;
    public String image1 = "";
    public String image2 = "";
    public String image3 = "";
    public boolean isSpec;
    public long pushTime;
    public long numbers;

    public boolean reviewed;
    public float monthSellCount;
    public float score;
}
