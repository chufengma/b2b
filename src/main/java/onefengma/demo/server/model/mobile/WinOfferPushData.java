package onefengma.demo.server.model.mobile;

import onefengma.demo.server.model.product.IronBuyBrief;

/**
 * @author yfchu
 * @date 2016/8/19
 */
public class WinOfferPushData extends BasePushData {
    public IronBuyBrief ironBuyBrief;

    public WinOfferPushData(String userId) {
        super(userId, PUSH_TYPE_WIN_OFFER);
    }
}
