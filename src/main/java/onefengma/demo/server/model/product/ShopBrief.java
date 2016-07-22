package onefengma.demo.server.model.product;

import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.services.funcs.CityDataHelper;

import java.math.BigDecimal;

/**
 * Created by chufengma on 16/6/18.
 */
public class ShopBrief {
    public String userId;
    public String companyName;
    public String officeAddress;
    public String cover;
    public String ironTypeDesc;
    public String handingTypeDesc;
    public BigDecimal ironCount = new BigDecimal(0);
    public BigDecimal handingCount = new BigDecimal(0);
    public BigDecimal ironMoney = new BigDecimal(0);
    public BigDecimal handingMoney = new BigDecimal(0);

    public BigDecimal money = new BigDecimal(0);
    public BigDecimal count = new BigDecimal(0);
    public float score;

    public int productNumbers;
}
