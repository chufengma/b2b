package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.model.apibeans.AuthSession;
import onefengma.demo.server.model.product.IronBuy;
import onefengma.demo.server.services.user.UserDataHelper;

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
    public long length;
    public long width;
    public long height;
    public String toleranceFrom;
    public String toleranceTo;
    public long numbers;
    public long timeLimit;

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
        ironBuy.pushTime = System.currentTimeMillis();

        ironBuy.length = length;
        ironBuy.width = width;
        ironBuy.height = height;
        ironBuy.tolerance = toleranceFrom + "-" + toleranceTo;
        ironBuy.numbers = numbers;
        ironBuy.timeLimit = timeLimit;
        return ironBuy;
    }
}
