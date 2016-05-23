package onefengma.demo.server.config;

import spark.Spark;

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

    private static DataBaseModel dataBaseModel;

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
    }

    public boolean isDev() {
        return ENV == ENV.DEV;
    }

    public DataBaseModel getDataBaseModel() {
        if (dataBaseModel == null) {
            dataBaseModel = new DataBaseModel(DATA_BASE_URL, DATA_BASE_USER, DATA_BASE_PASS);
        }
        return dataBaseModel;
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
