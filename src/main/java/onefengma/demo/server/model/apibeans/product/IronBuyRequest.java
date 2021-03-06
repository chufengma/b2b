package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.IdUtils;
import onefengma.demo.common.StringUtils;
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
    @NotRequired
    public String message = "";

    public String length;
    public String width;
    public String height;
    public String toleranceFrom;
    public String toleranceTo;
    public float numbers;
    public long timeLimit;
    public String unit;
    @NotRequired
    public String ironId;

    public IronBuy generateIronBug() {
        IronBuy ironBuy = new IronBuy();
        ironBuy.id = StringUtils.isEmpty(ironId) ? IdUtils.id() : ironId;
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
        ironBuy.unit = unit;
        ironBuy.appFlag = getMobileFlag();
        return ironBuy;
    }
}
