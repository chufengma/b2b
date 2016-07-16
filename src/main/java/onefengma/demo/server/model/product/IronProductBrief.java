package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/6/25.
 */
public class IronProductBrief {
    public String proId;
    public String cover;
    public String surface;
    public String ironType;
    public String material;
    public float price;
    public float monthSellCount;
    public float score;
    public boolean isSpec;

    public String sourceCityId;
    private String sourceCity;

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceCity() {
        return sourceCity;
    }
}
