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

    private static final String USER_TABLE = "user";
    private static final String USER_ID = "userId";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String MOBILE = "mobile";

    public User findUserByMobile(String mobile) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select * from @USER_TABLE where @MOBILE=:mobile");
        List<User> users;
        try (Connection con = getConn()) {
            users = con.createQuery(sql)
                    .addParameter("mobile", mobile)
                    .executeAndFetch(User.class);
            if (users.isEmpty()) {
                return null;
            }
        }
        return users.get(0);
    }

    public void insertUser(User user) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("insert into @USER_TABLE(@USER_ID, @NAME, @PASSWORD, @MOBILE) values (:id, :name, :pass, :mobile)");
        try (Connection con = getConn()) {
            con.createQuery(sql).addParameter("id", user.getId())
                    .addParameter("name", user.getName())
                    .addParameter("pass", user.getPassword())
                    .addParameter("mobile", user.getMobile())
                    .executeUpdate();
        }
    }

    public List<User> getUserList() throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select * from @USER_TABLE");
        List<User> userList = new ArrayList<>();
        try (Connection con = getConn()) {
            List<User> users = con.createQuery(sql).executeAndFetch(User.class);
            userList.addAll(users);
        }
        return userList;
    }
}
