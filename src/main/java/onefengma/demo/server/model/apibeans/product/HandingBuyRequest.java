package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.product.HandingBuy;

/**
 * Created by chufengma on 16/6/5.
 */
public class HandingBuyRequest extends AuthSession {
    public String handingType;
    public String souCityId;
    public String message;

    public HandingBuy generateBuy() {
        HandingBuy handingBuy = new HandingBuy();
        handingBuy.id = IdUtils.id();
        handingBuy.handingType = handingType;
        handingBuy.message = message;
        handingBuy.souCityId = souCityId;
        return handingBuy;
    }
}
