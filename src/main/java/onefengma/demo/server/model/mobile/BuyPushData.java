package onefengma.demo.server.model.mobile;

import onefengma.demo.server.model.product.IronBuyBrief;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class BuyPushData extends BasePushData {
    public int newSupplyNums;
    public IronBuyBrief ironBuyBrief;

    public BuyPushData(String userId, String type) {
        super(userId, type);
        if (ironBuyBrief != null) {
            this.id = ironBuyBrief.id;
        }
    }
}
