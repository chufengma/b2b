package onefengma.demo.server.model.product;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingBuy {
    public String id;
    public String handingType;
    public String souCityId;
    public String message;
    public String userId;
    public long pushTime;
    public long timeLimit;
    public int status = 0; // 0 正常状态, 1 交易完成,  2 过期
    public int salesmanId;
}
