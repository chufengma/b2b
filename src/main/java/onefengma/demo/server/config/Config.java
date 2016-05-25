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
    private static final String DATA_BASE_URL = "jdbc:mysql://localhost:3306/b2b?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATA_BASE_USER = "root";
    private static final String DATA_BASE_PASS = "8686239";

    private static final String NOT_FOUND_PATH = "404.html";
    private static final String BASE_FILE_PATH = "./files/";

    private static DataBaseModel dataBaseModel;
    private static FreeMarkerEngine freeMarkerEngine;

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
        Spark.externalStaticFileLocation("./res");

        // free marker engine
        freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        try {
            freeMarkerConfiguration.setTemplateLoader(new FileTemplateLoader(new File("./res/temples")));
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
            System.exit(-1);
        }
    }

    public boolean isDev() {
        return ENV == ENV.DEV;
    }

    public FreeMarkerEngine getFreeMarkerEngine() {
        return freeMarkerEngine;
    }

    public String getNotFoundPath() {
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

}
