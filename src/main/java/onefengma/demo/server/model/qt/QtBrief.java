package onefengma.demo.server.model.qt;

/**
 * Created by chufengma on 16/8/21.
 */
public class QtBrief {
    public String qtId;
    public int salesmanId;
    public String ironBuyId;
    public int status; // 0等待质检 1质检完成 2质检取消
    public long pushTime;
    public long finishTime;
    public String userId;
}
