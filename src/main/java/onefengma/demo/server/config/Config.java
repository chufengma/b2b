package onefengma.demo.server.config;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;

import com.tinify.Tinify;
import freemarker.cache.FileTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import onefengma.demo.server.model.metaData.IconDataCategory;
import onefengma.demo.server.model.metaData.City;
import onefengma.demo.server.core.LogUtils;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Config {

    // environment
    public static final ENVI ENV = ENVI.DEV;
    // data
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static final String DATA_BASE_USER = "root";
    private static final String DATA_BASE_PASS = "8686239";

    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String INDEX_PATH = "/index.html";
    private static final String INNER_ERROR_PATH = "/404.html";

    private static final String DOWN_LOAD_FILE_PATH = "./res/files/";
    private static final String DOWN_LOAD_FILE_REQUEST_PATH = "files/";
    private static final String VALIDATE_PATH = "./res/validate/";
    private static final String BASE_PAGE_PATH = "./res/B2BPlatformFront/";
    private static final String BASE_META_PATH = "./res/meta/";

    public static final String DEFAULT_AVATAR_URL = "./files/2016/6/19/WdDfdnobGk7Y.jpg";

    public static final String LOG_FILE_PREFIX = "/root/data/logs/";

    public static final boolean LOG_OPEN = false;

    public static final int PORT = 80;
    private static String HOST;

    private static DataBaseModel dataBaseModel;
    private static FreeMarkerEngine freeMarkerEngine;

    private static final int SESSION_DIE_TIME = 30 * 60;

    private static Config instance;

    public static Config instance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public enum ENVI {
        PRO,
        DEV
    }

    public void init() {
        // host
        try {
            HOST = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            HOST = "127.0.0.1";
            e.printStackTrace();
        }

        // config port
        Spark.port(PORT);

        // static files
        Spark.externalStaticFileLocation("./res/B2BPlatformFront/");

        // free marker engine
        freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        try {
            freeMarkerConfiguration.setTemplateLoader(new FileTemplateLoader(new File("./res/B2BPlatformFront")));
            freeMarkerConfiguration.setEncoding(new Locale("zh"), "utf8");
            freeMarkerConfiguration.setTemplateExceptionHandler(new TemplateExceptionHandler() {
                @Override
                public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
                    try {
                        out.write("[ERROR: " + te.getFTLInstructionStack() + "]");
                    } catch (IOException e) {
                        throw new TemplateException("Failed to print error message. Cause: " + e, env);
                    }
                }
            });
            freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        } catch (IOException e) {
            Spark.stop();
            LogUtils.i(e.toString());
        }

        Tinify.setKey("wMGvp7X-Sk02pI1vkouapzIKt3_6m84j");
    }

    public boolean isDev() {
        return ENV == ENV.DEV;
    }

    public FreeMarkerEngine getFreeMarkerEngine() {
        return freeMarkerEngine;
    }

    public static String getNotFoundPath() {
        return NOT_FOUND_PATH;
    }

    public DataBaseModel getDataBaseModel() {
        if (dataBaseModel == null) {
            dataBaseModel = new DataBaseModel(DATA_BASE_URL, DATA_BASE_USER, DATA_BASE_PASS);
        }
        return dataBaseModel;
    }

    public static String getDownLoadFilePath() {
        return DOWN_LOAD_FILE_PATH;
    }

    public static class DataBaseModel {
        private String url;
        private String user;
        private String password;

        public DataBaseModel(String url, String user, String password) {
            this.url = url;
            this.user = user;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static int getSessionDieTime() {
        return SESSION_DIE_TIME;
    }

    public static String getValidatePath() {
        File file = new File(VALIDATE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return VALIDATE_PATH;
    }

    public static String getBasePagePath() {
        return BASE_PAGE_PATH;
    }

    public static String getIndexPath() {
        return INDEX_PATH;
    }

    public static String getInnerErrorPath() {
        return INNER_ERROR_PATH;
    }

    public static IconDataCategory getIconDataCategory() {
        return MetaDataHelper.getIconDataCategory();
    }

    public static String getBaseMetaPath() {
        return BASE_META_PATH;
    }

    public static List<City> getCities() {
        return MetaDataHelper.getCities();
    }

    public static String getDownLoadFileRequestPath() {
        return DOWN_LOAD_FILE_REQUEST_PATH;
    }

    public static String getAddressPrefix() {
        return "http://" + HOST + ":" + PORT + "/";
    }
}
