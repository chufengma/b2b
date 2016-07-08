package onefengma.demo.server.services.user;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.Admin;
import onefengma.demo.server.model.admin.AdminUsersResponse;
import org.sql2o.Connection;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chufengma on 16/7/2.
 */
public class AdminDataManager extends BaseDataHelper {

    private static AdminDataManager adminDataManager;

    public static AdminDataManager instance() {
        if (adminDataManager == null) {
            adminDataManager = new AdminDataManager();
        }
        return adminDataManager;
    }

    public Admin getAdmin(String userName) {
        String sql = "select * from admin_user where userName=:userName";
        try (Connection conn = getConn()) {
            List<Admin> adminList = conn.createQuery(sql).addParameter("userName", userName).executeAndFetch(Admin.class);
            if (adminList.isEmpty()) {
                return null;
            }
            return adminList.get(0);
        }
    }

    public void deleteUser(String userId) throws Exception {
        String sql = "delete from user where userId=:userId";
        String bakSql = "insert into user_bak select * from user where userId=:userId";

        try (Connection conn = getConn()) {
            conn.createQuery(bakSql).addParameter("userId", userId).executeUpdate();
            conn.createQuery(sql).addParameter("userId", userId).executeUpdate();
        }
    }

    public void updateUser(String userId, int salesmanId) {
        String sql = "update user set salesManId=:salesManId where userId=:userId";

        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("userId", userId).addParameter("salesManId", salesmanId).executeUpdate();
        }
    }

    public AdminUsersResponse getBuyer(PageBuilder pageBuilder) {
        String whereSql = pageBuilder.generateWhere();
        String maxCountSql = "select count(*) from user,salesman where salesman.id=buySellerId " + ((StringUtils.isEmpty(whereSql)) ? "" : " and " + whereSql);
        String userSalesMoneySql = "select userId, mobile,registerTime, sum(ironMoney + handingMoney)  as buyMoney , tel as salesTel, salesmanId as salesId " +
                "from (select userId, mobile,registerTime,salesmanId,tel from user,salesman where salesmanId=salesman.id) as userComplete" +
                " left join seller_amount on (userId=sellerId and day <=:endTime and day>:startTime ) "
                + (StringUtils.isEmpty(pageBuilder.generateWhere()) ? "" : " where " + whereSql)
                + " group by userId  order by buyMoney desc" + pageBuilder.generateLimit();
        try (Connection conn = getConn()) {
            AdminUsersResponse usersResponse = new AdminUsersResponse();
            usersResponse.buyers = conn.createQuery(userSalesMoneySql)
                    .addParameter("startTime", pageBuilder.startTime)
                    .addParameter("endTime", pageBuilder.endTime)
                    .executeAndFetch(BuyerBrief.class);
            Long maxCount = conn.createQuery(maxCountSql).executeScalar(Long.class);
            usersResponse.maxCount = (maxCount == null ? 0 : maxCount);
            return usersResponse;
        }
    }

    public static class BuyerBrief {
        public String mobile;
        private long registerTime;
        public int salesId;
        public String salesTel;
        public float buyMoney;
        public String userId;

        public long getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(long registerTime) {
            this.registerTime = registerTime;
        }
    }

}
