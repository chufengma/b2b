package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;

/**
 * Created by chufengma on 16/7/10.
 */
public class InnerMessageDataHelper extends BaseDataHelper {

    private static InnerMessageDataHelper instance;

    public static InnerMessageDataHelper instance() {
        if (instance == null) {
            instance = new InnerMessageDataHelper();
        }
        return instance;
    }

    public void addRegisterSuccessMessage(String userId) {
        addInnerMessage(userId, "欢迎加入淘不锈", "淘不锈是全国首家提供上门质检的不锈钢电商平台，恭喜您注册成为淘不锈会员，开启您的淘不锈之旅吧，一言不和就上门质检");
    }

    public void addInnerMessage(String userId, String title, String message) {
        String sql = "insert into inner_message set title=:title, userId=:userId, message=:message, pushTime=:time ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("title", title)
                    .addParameter("message", message)
                    .addParameter("time", System.currentTimeMillis())
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public boolean isMessageUserRight(String userId, int id) {
        String sql = "select userId from inner_message where id=:id";
        try(Connection conn = getConn()) {
            String userQuery = conn.createQuery(sql).addParameter("id", id).executeScalar(String.class);
            return StringUtils.equals(userQuery, userId);
        }
    }

    public void deleteInnerMessage(int id) {
        String sql = "delete from inner_message where id=:id ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql)
                    .addParameter("id", id).executeUpdate();
        }
    }

    public InnerMessageDetail getInnerMessageDetail(int id) {
        String sql = "select " + generateFiledString(InnerMessageDetail.class) + " from inner_message where id=:id";
        String updateSql = "update inner_message set reviewed=true where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(updateSql).addParameter("id", id).executeUpdate();
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(InnerMessageDetail.class);
        }
    }

    public int getInnerMessageCountByUser(String userId) {
        String sql = "select count(*) from inner_message where userId=:userId";
        try(Connection conn = getConn()) {
            Integer count = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return count == null ? 0 : count;
        }
    }

    public List<InnerMessageBrief> getInnerMessages(PageBuilder pageBuilder, String userId) {
        String sql = "select " + generateFiledString(InnerMessageBrief.class) + " from inner_message where userId=:userId  order by pushTime desc " + pageBuilder.generateLimit();
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("userId", userId).executeAndFetch(InnerMessageBrief.class);
        }
    }

    public static class InnerMessageDetail {
        public String id;
        public String title;
        public String message;
        public long pushTime;
        public boolean reviewed;
    }

    public static class InnerMessageBrief {
        public String id;
        public String title;
        public long pushTime;
        public boolean reviewed;
    }

}
