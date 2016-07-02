package onefengma.demo.server.model.apibeans;

import onefengma.demo.annotation.NotRequired;

/**
 * Created by chufengma on 16/6/5.
 */
public class BasePageBean extends BaseBean {
    public int currentPage;
    public int pageCount;

    @NotRequired
    public String score;
    @NotRequired
    public String price;
    @NotRequired
    public String monthSellCount;
    @NotRequired
    public String keyword;
    @NotRequired
    public String productCount;
    @NotRequired
    public String monthSellMoney;

    @NotRequired
    public int productType = -2 ; // 0 iron, 1 handing

}
