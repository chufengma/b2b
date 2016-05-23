package onefengma.demo.server;

import java.util.Arrays;
import java.util.List;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.user.UserManager;

/**
 * @author yfchu
 * @date 2016/4/1
 */
public class Enter {

    private static List<BaseManager> managers = Arrays.asList(new UserManager());

    public static void main(String[] args) {
        // user modules
        for(BaseManager manager : managers) {
            manager.init();
        }
    }

}
