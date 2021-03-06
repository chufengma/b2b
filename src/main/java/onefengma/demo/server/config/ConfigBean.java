package onefengma.demo.server.config;

/**
 * @author yfchu
 * @date 2016/8/8
 */
public class ConfigBean {

    public static String DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static String DATA_BASE_USER = "root";
    public static String DATA_BASE_PASS = "8686239";
    public static String LOG_FILE_PREFIX = "/root/data/logs-dev/";
    public static int PORT = 80;
    public static int USER_MESSAGE_PORT = 9091;
    public static String MOBILE_PUSH_PREFIX = "PRO_";

    public static void configDev() {
        DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b_dev?useUnicode=true&characterEncoding=utf8&useSSL=false";
        DATA_BASE_USER = "root";
        DATA_BASE_PASS = "8686239";
        LOG_FILE_PREFIX = "/root/data/logs-dev/";
        PORT = 9090;
        USER_MESSAGE_PORT = 9092;
        MOBILE_PUSH_PREFIX = "DEV_";
    }

    public static void configPro() {
        DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=utf8&useSSL=false";
        DATA_BASE_USER = "root";
        DATA_BASE_PASS = "8686239";
        LOG_FILE_PREFIX = "/root/data/logs/";
        PORT = 80;
        USER_MESSAGE_PORT = 9091;
        MOBILE_PUSH_PREFIX = "PRO_";
    }

}
