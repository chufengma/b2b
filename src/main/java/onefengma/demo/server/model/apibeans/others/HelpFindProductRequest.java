package onefengma.demo.server.model.apibeans.others;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;

/**
 * @author yfchu
 * @date 2016/7/15
 */
public class HelpFindProductRequest extends BaseBean {
    public String mobile;
    public String type;
    public String city;
    @NotRequired
    public String comment;

    public HelpFindProduct generateHelpFind() {
        HelpFindProduct helpFindProduct = new HelpFindProduct();
        helpFindProduct.applyTime = System.currentTimeMillis();
        helpFindProduct.city = city;
        helpFindProduct.comment = comment;
        helpFindProduct.type = type;
        helpFindProduct.mobile = mobile;
        return helpFindProduct;
    }
}
