package onefengma.demo.server.services.user;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.UpdateBuilder;
import onefengma.demo.server.model.SalesMan;
import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

import onefengma.demo.server.model.Seller;
import onefengma.demo.server.model.User;
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
    private static final String IS_SELLER = "isSeller";

    private static UserDataHelper userDataHelper;

    public static UserDataHelper instance() {
        if (userDataHelper == null) {
            userDataHelper = new UserDataHelper();
        }
        return userDataHelper;
    }

    public User findUserByMobile(String mobile) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select " + generateFiledString(User.class) + " from @USER_TABLE where @MOBILE=:mobile");
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

    public User findUserByUserId(String userId) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select " + generateFiledString(User.class) + "from @USER_TABLE where @USER_ID=:userId");
        List<User> users;
        try (Connection con = getConn()) {
            users = con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(User.class);
            if (users.isEmpty()) {
                return null;
            }
        }
        return users.get(0);
    }

    public void changeUserPassword(String userId, String password) {
        String sql = "update user set password=:password where userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("password", password)
                    .addParameter("userId", userId).executeUpdate();
        }
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

    public void setSeller(String userId, boolean seller) throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("update @USER_TABLE set @IS_SELLER = :seller where @USER_ID=:userId");
        try (Connection conn = getConn()){
            conn.createQuery(sql).addParameter("seller", seller)
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public List<User> getUserList() throws NoSuchFieldException, IllegalAccessException {
        String sql = createSql("select  " + generateFiledString(User.class) + " from @USER_TABLE");
        List<User> userList = new ArrayList<>();
        try (Connection con = getConn()) {
            List<User> users = con.createQuery(sql).executeAndFetch(User.class);
            userList.addAll(users);
        }
        return userList;
    }

    public SalesMan getSalesMan(String userId) {
        String sql = "select salesman.id, salesman.name, salesman.tel from salesman,user " +
                "where user.salesManId=salesman.id and userId=:userId";
        try(Connection conn = getConn()) {
            SalesMan salesMan = conn.createQuery(sql).addParameter("userId", userId).executeAndFetchFirst(SalesMan.class);
            return salesMan;
        }
    }

    public SalesMan getSalesManById(int salesId) {
        String sql = "select " + generateFiledString(SalesMan.class) + " from salesman where id=:id";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", salesId).executeAndFetchFirst(SalesMan.class);
        }
    }

    public boolean isSalesManExited(int salesId) {
        return getSalesManById(salesId) != null;
    }

    public String getSalesManTel(String userId) {
        SalesMan salesMan = getSalesMan(userId);
        return salesMan == null ? "" : salesMan.tel;
    }

    public String getUserMobile(String userId) {
        String sql = "select mobile from user where userId=:userId";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("userId", userId).executeScalar(String.class);
        }
    }

    public void updateUserProfile(UpdateBuilder updateBuilder, String userId) {
        String updateSql = updateBuilder.generateSql();
        if (StringUtils.isEmpty(updateSql)) {
            return;
        }
        String sql = "update user set " + updateSql  + " where userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
        }
    }
}
