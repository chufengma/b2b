package onefengma.demo.server.model.product;

import java.math.BigDecimal;

/**
 * Created by chufengma on 16/6/25.
 */
public class HandingProductBrief {
    public String id;
    public String type;
    public String souCityId;
    public String title;
    public BigDecimal price;
    public String unit;
    public String cover;
    public BigDecimal monthSellCount;
    public float score;

    private String sourceCity;

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
