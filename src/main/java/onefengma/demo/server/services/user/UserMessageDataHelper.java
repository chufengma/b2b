package onefengma.demo.server.services.user;

import onefengma.demo.rx.UserMessageServer;
import org.sql2o.Connection;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * Created by chufengma on 16/6/18.
 */
public class UserMessageDataHelper extends BaseDataHelper {

    private static UserMessageDataHelper instance;

    public static UserMessageDataHelper instance() {
        if (instance == null) {
            instance = new UserMessageDataHelper();
        }
        return instance;
    }

    public String getUserMessage(String userId) {
        String sql = "select message from user_message where userId=:userId";
        String deleteSql = "delete from user_message where userId=:userId";
        try(Connection conn = getConn()) {
            String message = conn.createQuery(sql).addParameter("userId", userId).executeScalar(String.class);
            conn.createQuery(deleteSql).addParameter("userId", userId).executeUpdate();
            return message;
        }
    }

    public void setUserMessage(String userId, String message) {
        String sql = "replace user_message set userId=:userId,message=:message";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId).addParameter("message", message).executeUpdate();
        }
        UserMessageServer.getInstance().sendUserMessage(userId, message);
    }

    public static class UserMessage {
        public String userId;
        public String message;
    }

}
