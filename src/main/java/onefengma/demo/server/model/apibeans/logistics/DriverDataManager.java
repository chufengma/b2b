package onefengma.demo.server.model.apibeans.logistics;

import onefengma.demo.common.IdUtils;
import onefengma.demo.server.services.funcs.DataManager;
import org.sql2o.Connection;

/**
 * Created by dev on 2017/2/27.
 */
public class DriverDataManager extends DataManager {

    private static DriverDataManager instance;

    public static DriverDataManager instance() {
        if (instance == null) {
            instance = new DriverDataManager();
        }
        return instance;
    }

    public void insertDriver(String mobile, String password) {
        String sql = "insert into logistics_driver set id=:id, mobile=:mobile, password=:password";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", IdUtils.id())
                    .addParameter("mobile", mobile)
                    .addParameter("password", password)
                    .executeUpdate();
        }
    }

    public void changePasswordDriver(String mobile, String password) {
        String sql = "update logistics_driver set password=:password where mobile=:mobile";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("mobile", mobile)
                    .addParameter("password", password)
                    .executeUpdate();
        }
    }

    public LogisticsDriver getDriverDesc(String id) {
        String sql = "select " + generateFiledString(LogisticsDriver.class) + " from logistics_driver where id=:id";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(LogisticsDriver.class);
        }
    }

    public LogisticsDriver getDriverDescByMobile(String mobile) {
        String sql = "select " + generateFiledString(LogisticsDriver.class) + " from logistics_driver where mobile=:mobile";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("mobile", mobile).executeAndFetchFirst(LogisticsDriver.class);
        }
    }

    public void fillCompanyName(String id, String companyName) {
        String sql = "update logistics_driver set companyName=:companyName where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("companyName", companyName).addParameter("id", id).executeUpdate();
        }
    }

    public static class LogisticsDriver {
        public String id;
        public String mobile;
        public String password;
        public String companyName;
        public String license;
        public String tax;
        public String companyCode;
        public String road;
        public String account;
        public String car1;
        public String car2;
        public String car3;
    }

}
