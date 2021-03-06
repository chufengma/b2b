package onefengma.demo.server.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import onefengma.demo.common.DateHelper;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.config.ConfigBean;

/**
 * @author yfchu
 * @date 2016/5/20
 */
public class LogUtils {

    private static final Logger logger = Logger.getLogger("DemoLog");

    public static void i(String log) {
        if (Config.instance().isDev()) {
            logger.info(log);
            saveToFiles(log, false);
        }
    }

    public static void i(String log, boolean forceLog) {
        if (Config.instance().isDev()) {
            logger.info(log);
            saveToFiles(log, forceLog);
        }
    }

    public static void e(Exception e, String log) {
        if (Config.instance().isDev()) {
            logger.info(log);
            saveToFiles(log, false);
            e.printStackTrace();
        }
    }

    public static void saveToFiles(String log, boolean forceLog) {
        if (!Config.LOG_OPEN && !forceLog) {
            return;
        }
        FileWriter writer = null;
        try {
            String folder = ConfigBean.LOG_FILE_PREFIX + DateHelper.getDataStr() + "/";
            new File(folder).mkdirs();
            String fileName = folder + DateHelper.getDataStr() + ".log";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(DateHelper.getDataFullStr() + " : " + log + "\r\n");
            writer.close();
        } catch (IOException e) {
        }
    }

}
