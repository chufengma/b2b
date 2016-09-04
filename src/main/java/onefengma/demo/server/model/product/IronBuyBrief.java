package onefengma.demo.server.model.product;

import java.math.BigDecimal;

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

    public String length;
    public String width;
    public String height;
    public String tolerance;
    public String unit;
    public BigDecimal numbers;
    public long timeLimit;
    public int status; // 0 待报价, 1 交易完成 , 2 过期,  3 已报价候选中, 4 我已中标, 5 已删除, 6 流标
    public String supplyUserId;
    public long supplyWinTime;
    public long lastGetDetailTime;
    public int newSupplyNum;
    public int editStatus; // 0表示未编辑,1 表示已编辑

    private int supplyCount;

    public int getSupplyCount() {
        return supplyCount;
    }

    public void setSupplyCount(int supplyCount) {
        this.supplyCount = supplyCount;
    }


    private String sourceCity;

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
}
