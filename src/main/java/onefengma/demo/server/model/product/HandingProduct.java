package onefengma.demo.server.model.product;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingProduct {
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
    public long pushTime;

    @NotRequired
    private String sourceCity;

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
