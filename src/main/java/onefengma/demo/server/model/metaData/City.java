package onefengma.demo.server.model.metaData;

import java.util.ArrayList;

import onefengma.demo.common.IdUtils;

/**
 * @author yfchu
 * @date 2016/6/1
 */
public class City {
    public String name;
    public ArrayList<City> sub;
    public int type = -1;
    public String id;

    public void setId(String id) {
        this.id = id;
        if (sub == null) {
            return;
        }
        for(City city : sub) {
            city.setId(IdUtils.id());
        }
    }
}
