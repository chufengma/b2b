package onefengma.demo.server.services.user;

import org.sql2o.Connection;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/9/1
 */
public class SalesDataHelper extends BaseDataHelper {

    private static SalesDataHelper instance;

    public static SalesDataHelper instance() {
        if (instance == null) {
            instance = new SalesDataHelper();
        }
        return instance;
    }

    public SalesManDetail getSalesMan(String tel) {
        String sql = "select * from salesman where tel=:tel";
        try (Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("tel", tel).executeAndFetchFirst(SalesManDetail.class);
        }
    }

    public static class SalesManDetail {
        public int id;
        public String name;
        public String tel;
        public String password;
        public long bindTime;
    }

}
