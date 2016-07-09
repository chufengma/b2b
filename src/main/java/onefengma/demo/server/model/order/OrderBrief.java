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
    public float count;
    public int status; // 0 待确认, 1 已确认 未评价 2 已评价  3 已取消  4 已删除
    public int deleteBy; // 0 没有被删除 1 被买家删除 2 被卖家删除
    public int cancelBy; // 0 没有被取消 1 被买家取消 2 被卖家取消

    public String sellerMobile;
    public String buyerMobile;
}
