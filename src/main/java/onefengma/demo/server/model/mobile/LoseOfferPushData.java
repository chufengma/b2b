package onefengma.demo.server.model.mobile;

import onefengma.demo.server.model.product.IronBuyBrief;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class LoseOfferPushData extends BasePushData {
    public IronBuyBrief ironBuyBrief;

    public LoseOfferPushData(String userId) {
        super(userId, PUSH_TYPE_LOSE_OFFER);
        if (ironBuyBrief != null) {
            this.id = ironBuyBrief.id;
        }
    }
}
