package onefengma.demo.server.config;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import onefengma.demo.server.core.LogUtils;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class Config {

    // port
    private static final int PORT = 9090;
    // environment
    private static final ENVI ENV = ENVI.DEV;
    // data
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static final String DATA_BASE_USER = "root";
    private static final String DATA_BASE_PASS = "root";

    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String INDEX_PATH = "/index.html";
    private static final String INNER_ERROR_PATH = "/404.html";

    private static final String BASE_FILE_PATH = "./res/files/";
    private static final String VALIDATE_PATH = "./res/validate/";
    private static final String BASE_PAGE_PATH = "./res/views/";


    private static DataBaseModel dataBaseModel;
    private static FreeMarkerEngine freeMarkerEngine;

    private static final int SESSION_DIE_TIME = 10;

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
        // config port
        Spark.port(PORT);

        // static files
        Spark.externalStaticFileLocation("./res/views/");

        // free marker engine
        freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        try {
            freeMarkerConfiguration.setTemplateLoader(new FileTemplateLoader(new File("./res/views")));
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

    public static String getBaseFilePath() {
        return BASE_FILE_PATH;
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


}
