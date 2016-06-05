package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.product.IronBuy;

/**
 * Created by chufengma on 16/6/5.
 */
public class IronBuyRequest extends AuthSession {
    public String ironType;
    public String material;
    public String surface;
    public String proPlace;
    public String locationCityId;
    public String message;

    public IronBuy generateIronBug() {
        IronBuy ironBuy = new IronBuy();
        ironBuy.id = IdUtils.id();
        ironBuy.ironType = ironType;
        ironBuy.material = material;
        ironBuy.proPlace = proPlace;
        ironBuy.surface = surface;
        ironBuy.locationCityId = locationCityId;
        ironBuy.message = message;
        ironBuy.userId = getUserId();
        return ironBuy;
    }
}
