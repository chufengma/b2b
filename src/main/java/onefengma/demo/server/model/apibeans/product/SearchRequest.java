package onefengma.demo.server.model.apibeans.product;

import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.BasePageBean;

/**
 * Created by chufengma on 16/6/11.
 */
public class SearchRequest extends BasePageBean {
    public String keyword;
    // 0 = iron, 1 = handing, 2 = 店铺
    public int type;


    public SearchType getType() {
        return SearchType.values()[type];
    }

    public enum SearchType {
        IRON,
        HANDING,
        SHOP
    }
}
