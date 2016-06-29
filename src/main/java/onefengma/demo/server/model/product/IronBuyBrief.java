package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/6/29.
 */
public class IronBuyBrief {
    public String id;
    public String ironType;
    public String material;
    public String surface;
    public String proPlace;
    public String locationCityId;
    public String userId;
    public String message;
    public long pushTime;

    public long length;
    public long width;
    public long height;
    public String tolerance;
    public long numbers;
    public long timeLimit;


    private String sourceCity;

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
