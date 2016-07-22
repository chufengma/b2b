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
    public BigDecimal ironCount;
    public BigDecimal handingCount;
    public BigDecimal ironMoney;
    public BigDecimal handingMoney;

    public BigDecimal money;
    public BigDecimal count;
    public float score;

    public int productNumbers;
}
