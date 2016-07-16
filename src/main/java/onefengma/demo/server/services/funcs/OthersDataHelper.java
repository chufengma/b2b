package onefengma.demo.server.services.funcs;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * Created by chufengma on 16/6/18.
 */
public class OthersDataHelper extends BaseDataHelper {
    private static OthersDataHelper instance;

    public static OthersDataHelper instance() {
        if (instance == null) {
            instance = new OthersDataHelper();
        }
        return instance;
    }

}
