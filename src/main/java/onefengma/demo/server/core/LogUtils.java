package onefengma.demo.server.core;

import java.util.logging.Logger;

import onefengma.demo.server.config.Config;

/**
 * @author yfchu
 * @date 2016/5/20
 */
public class LogUtils {

    private static final Logger logger = Logger.getLogger("DemoLog");

    public static void i(String log) {
        if (Config.instance().isDev()) {
            logger.info(log);
        }
    }

    public static void e(Exception e, String log) {
        if (Config.instance().isDev()) {
            logger.info(log);
            e.printStackTrace();
        }
    }
}
