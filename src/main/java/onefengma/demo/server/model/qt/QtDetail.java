package onefengma.demo.server.model.qt;

import onefengma.demo.server.model.product.IronBuyBrief;

/**
 * Created by chufengma on 16/8/21.
 */
public class QtDetail {
    public String qtId;
    public int salesmanId;
    public String ironBuyId;
    public int status; // 0等待质检 1质检完成 2质检取消 3 质检中
    public long pushTime;
    public long startTime;
    public long finishTime;
    public String userId;
    private IronBuyBrief ironBuyBrief;

    public IronBuyBrief getIronBuyBrief() {
        return ironBuyBrief;
    }

    public void setIronBuyBrief(IronBuyBrief ironBuyBrief) {
        this.ironBuyBrief = ironBuyBrief;
    }

    public static QtDetail parse(QtBrief qtBrief) {
        QtDetail qtDetail = new QtDetail();
        qtDetail.qtId = qtBrief.qtId;
        qtDetail.salesmanId = qtBrief.salesmanId;
        qtDetail.ironBuyId = qtBrief.ironBuyId;
        qtDetail.status = qtBrief.status;
        qtDetail.pushTime = qtBrief.pushTime;
        qtDetail.finishTime = qtBrief.finishTime;
        qtDetail.userId = qtBrief.userId;
        return qtDetail;
    }

}
