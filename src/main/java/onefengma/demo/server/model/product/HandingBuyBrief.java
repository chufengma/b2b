package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/6/29.
 */
public class HandingBuyBrief {
    public String id;
    public String handingType;
    public String souCityId;
    public String message;
    public String userId;
    public long pushTime;
    public long timeLimit;
    public int status;   // 0 正常状态, 1 交易完成,  2 过期,  3 已报价候选中

    private int supplyCount;
    private String sourceCity;

    public int getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(int supplyCount) {
        this.supplyCount = supplyCount;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
