package onefengma.demo.server.services.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.sql2o.Connection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.UpdateBuilder;
import onefengma.demo.server.model.SalesMan;
import onefengma.demo.server.model.User;
import onefengma.demo.server.model.UserProfile;
import onefengma.demo.server.services.funcs.InnerMessageDataHelper;

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

    public boolean isUserExisted(String userId) {
        String sql = "select count(*) from user where userId=:userId";
        try(Connection conn = getConn()) {
            Integer integer = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return integer != null && integer > 0;
        }
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
        String sql = "select " + generateFiledString(User.class) + " from user where userId=:userId";
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
        String sql = createSql("insert into @USER_TABLE(@USER_ID, @NAME, @PASSWORD, @MOBILE, registerTime, avator) values (:id, :name, :pass, :mobile, :registerTime, :avator)");
        try (Connection con = getConn()) {
            con.createQuery(sql).addParameter("id", user.getId())
                    .addParameter("name", "用户")
                    .addParameter("pass", user.getPassword())
                    .addParameter("mobile", user.getMobile())
                    .addParameter("avator", Config.DEFAULT_AVATAR_URL)
                    .addParameter("registerTime", System.currentTimeMillis())
                    .executeUpdate();
        }
        InnerMessageDataHelper.instance().addRegisterSuccessMessage(user.getId());
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

    public String getSalesManIdByMobile(String mobile) {
        String sql = "select id from salesman where tel=:tel";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("tel", mobile).executeScalar(String.class);
        }
    }

    public int getSalesManId(String userId) {
        String sql = "select salesManId from user where userId=:userId";
        try(Connection conn = getConn()) {
            Integer id = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Integer.class);
            return id == null ? 0 : id;
        }
    }

    public String getUserIdByMobile(String mobile) {
        String sql = "select userId from user where mobile=:mobile";
        try(Connection conn = getConn()) {
           return conn.createQuery(sql).addParameter("mobile", mobile).executeAndFetchFirst(String.class);
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

    public UserProfile getUserProfile(String userId) {
        String sql = "select " + generateFiledStringExclude(UserProfile.class, "userData", "sellerData", "salesMan", "becomeStatus", "seller") + " from user where userId=:userId ";
        try(Connection conn = getConn()) {
            UserProfile userProfile = conn.createQuery(sql).addParameter("userId", userId).executeAndFetchFirst(UserProfile.class);
            if (userProfile != null) {
                userProfile.sellerData = getSellerInfo(userId);
                userProfile.userData = getUserInfo(userId);
                userProfile.salesMan = getSalesMan(userId);
                userProfile.becomeStatus = SellerDataHelper.instance().getBecomeSellerStatus(userId);
                userProfile.seller = SellerDataHelper.instance().getSeller(userId);
            }
            return userProfile;
        }
    }

    public float getBuyerIntegral(String userId) {
        String sql = "select integral from user where userId=:userId";
        try(Connection conn = getConn()) {
            Float integral = conn.createQuery(sql).addParameter("userId", userId).executeScalar(Float.class);
            return integral == null ? 0 : integral;
        }
    }

    public void bindSalesman(String userId, int salesmanId) {
        String sql = "update user set salesManId =:salesmanId,salesBindTime=:time  where userId=:userId";
        try(Connection conn= getConn()) {
            conn.createQuery(sql).addParameter("userId", userId)
                    .addParameter("time", System.currentTimeMillis())
                                    .addParameter("salesmanId", salesmanId).executeUpdate();
            updateSalesmanBindTime(salesmanId, userId);
        }
    }

    public UserInfo getUserInfo(String userId) {
        String sql = "select integral from user where userId=:userId";
        String ironBuyCountsql = "select count(*) from iron_buy where status = 0 and userId=:userId ";
        String handingBuyCountsql = "select count(*) from handing_buy where status = 0 and userId=:userId ";
        String orderCountSql = "select count(*) from product_orders where status = 0 and buyerId=:userId";
        try(Connection conn = getConn()) {
            BigDecimal integral = conn.createQuery(sql).addParameter("userId", userId).executeScalar(BigDecimal.class);
            integral = (integral == null) ? new BigDecimal(0) : integral;

            Integer ironCount = conn.createQuery(ironBuyCountsql).addParameter("userId", userId).executeScalar(Integer.class);
            ironCount = (ironCount == null) ? 0 : ironCount;

            Integer handingCount = conn.createQuery(handingBuyCountsql).addParameter("userId", userId).executeScalar(Integer.class);
            handingCount = (handingCount == null) ? 0 : handingCount;

            Integer orderCount = conn.createQuery(orderCountSql).addParameter("userId", userId).executeScalar(Integer.class);
            orderCount = (orderCount == null) ? 0 : orderCount;

            UserInfo userInfo = new UserInfo();
            userInfo.integral = integral;
            userInfo.buys = ironCount + handingCount;
            userInfo.orders = orderCount;

            return userInfo;
        }
    }

    public UserInfo getSellerInfo(String userId) {
        String sql = "select integral from seller where userId=:userId";
        String ironBuyCountsql = "select count(*) from iron_buy where status = 0 and userId=:userId ";
        String handingBuyCountsql = "select count(*) from handing_buy where status = 0 and userId=:userId ";
        String orderCountSql = "select count(*) from product_orders where status = 0 and sellerId=:userId";
        try(Connection conn = getConn()) {
            BigDecimal integral = conn.createQuery(sql).addParameter("userId", userId).executeScalar(BigDecimal.class);
            integral = (integral == null) ? new BigDecimal(0) : integral;

            Integer ironCount = conn.createQuery(ironBuyCountsql).addParameter("userId", userId).executeScalar(Integer.class);
            ironCount = (ironCount == null) ? 0 : ironCount;

            Integer handingCount = conn.createQuery(handingBuyCountsql).addParameter("userId", userId).executeScalar(Integer.class);
            handingCount = (handingCount == null) ? 0 : handingCount;

            Integer orderCount = conn.createQuery(orderCountSql).addParameter("userId", userId).executeScalar(Integer.class);
            orderCount = (orderCount == null) ? 0 : orderCount;

            UserInfo userInfo = new UserInfo();
            userInfo.integral = integral;
            userInfo.buys = ironCount + handingCount;
            userInfo.orders = orderCount;

            return userInfo;
        }
    }

    public static class UserInfo {
        public BigDecimal integral;
        public int buys;
        public int orders;
    }

    public void updateSalesmanBindTime(int salesmanId, String userId) {
        String salesManSql = "update salesman set bindTime=:time where id=:id";
        String userSql = "update user set salesBindTime=:time where userId=:userId";
        try(Connection conn= getConn()) {
            conn.createQuery(salesManSql).addParameter("id", salesmanId)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
            conn.createQuery(userSql).addParameter("userId", userId)
                    .addParameter("time", System.currentTimeMillis()).executeUpdate();
        }
    }

    public IronSubscribe getIronSubscribe(String userId) {
        String sql = "select * from seller_subscribe where userId=:userId";
        try(Connection conn = getConn()) {
            IronSubscribeCache cache = conn.createQuery(sql).addParameter("userId", userId).executeAndFetchFirst(IronSubscribeCache.class);
            IronSubscribe ironSubscribe = new IronSubscribe();
            try {
                ironSubscribe.materials = JSON.parseObject(cache.materials, new TypeReference<List<String>>(){});
                ironSubscribe.proPlaces = JSON.parseObject(cache.proPlaces, new TypeReference<List<String>>(){});
                ironSubscribe.surfaces = JSON.parseObject(cache.surfaces, new TypeReference<List<String>>(){});
                ironSubscribe.types = JSON.parseObject(cache.types, new TypeReference<List<String>>(){});
            } catch (Exception e) {}
            return ironSubscribe;
        }
    }

    public void updateIronSubscribe(String userId, String types, String surfaces, String materials, String proPlaces) {
        String sql = "replace seller_subscribe set types=:types,surfaces=:surfaces,materials=:materials,proPlaces=:proPlaces,userId=:userId";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("types", types)
                    .addParameter("surfaces", surfaces)
                    .addParameter("materials", materials)
                    .addParameter("proPlaces", proPlaces)
                    .addParameter("userId", userId).executeUpdate();
        }
    }

    public static class IronSubscribe {
        public String userId;
        public List<String> types;
        public List<String> surfaces;
        public List<String> materials;
        public List<String> proPlaces;
    }

    public static class IronSubscribeCache {
        public String userId;
        public String types;
        public String surfaces;
        public String materials;
        public String proPlaces;
    }
}
