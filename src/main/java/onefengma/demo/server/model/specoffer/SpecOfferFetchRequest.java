package onefengma.demo.server.model.specoffer;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.server.model.apibeans.BaseBean;
import onefengma.demo.server.model.apibeans.BasePageBean;

import java.io.File;

/**
 * Created by chufengma on 2017/5/1.
 */
public class SpecOfferFetchRequest extends BasePageBean {
    @NotRequired
    public String key;
    @NotRequired
    public String type;
    @NotRequired
    public String material;
    @NotRequired
    public String surface;
    @NotRequired
    public String proPlace;
}
