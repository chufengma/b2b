package onefengma.demo.server.model.order;

/**
 * Created by chufengma on 16/7/3.
 */
public class OrderBrief {
    public int productType;
    public String id;
    public long sellTime;
    public long timeLimit;
    public String cover;
    public String desc;
    public String city;
    public float price;
    public float sellMoney;
    public int status; // 0 待确认, 1 已确认 未评价 2 已评价

    public String sellerMobile;
}
