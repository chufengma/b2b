package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/6/25.
 */
public class HandingProductBrief {
    public String id;
    public String type;
    public String souCityId;
    public String title;
    public float price;
    public String unit;
    public String cover;
    public float monthSellCount;
    public float score;

    private String sourceCity;

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
