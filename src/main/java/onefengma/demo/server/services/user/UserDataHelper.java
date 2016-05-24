package onefengma.demo.server.services.user;

import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.model.User;
import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public class UserDataHelper extends BaseDataHelper {

    public void insertUser(User user) {
        String sql = "insert into user(id, name, password) values (:id, :name, :pass)";
        try (Connection con = getConn()) {
            con.createQuery(sql).addParameter("id", user.getId())
                    .addParameter("name", user.getName())
                    .addParameter("pass", user.getPassword())
                    .executeUpdate();
        }
    }

    public List<User> getUserList() {
        String sql = "select * from user";
        List<User> userList = new ArrayList<>();
        try (Connection con = getConn()) {
            List<User> users = con.createQuery(sql).executeAndFetch(User.class);
            userList.addAll(users);
        }
        return userList;
    }
}
