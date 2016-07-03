package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/7/3.
 */
public class HandingDetail {
    public String id;
    public String type;
    public String souCityId;
    public String title;
    public float price;
    public String unit;
    public String cover;
    public String image1;
    public String image2;
    public String image3;
    public String userId;


    public boolean reviewed;
    public float monthSellCount;
    public float score;

    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
