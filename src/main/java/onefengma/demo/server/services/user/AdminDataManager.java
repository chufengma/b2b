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

    public AdminUsersResponse getBuyer(PageBuilder pageBuilder) {
        String whereSql = pageBuilder.generateWhere();
        whereSql = StringUtils.isEmpty(whereSql) ? "" : " and " + whereSql;
        String sql = "select userId, mobile,registerTime,salesman.id as salesId,tel from user,salesman where salesman.id=buySellerId " + whereSql + " order by registerTime" + pageBuilder.generateLimit();
        String maxCountSql = "select count(*) from user,salesman where salesman.id=buySellerId " + whereSql;
        String moneySql = "select sum(totalMoney) as totalMoney from product_orders where buyerId=:buyerId and sellTime<:endTime and sellTime>=:startTime";
        try (Connection conn = getConn()) {
            Table table = conn.createQuery(sql).executeAndFetchTable();
            List<Row> rows = table.rows();
            List<BuyerBrief> buyerBriefs = new ArrayList<>();
            for (Row row : rows) {
                BuyerBrief buyerBrief = new BuyerBrief();
                buyerBrief.mobile = row.getString("mobile");
                buyerBrief.registerTime = row.getLong("registerTime");
                buyerBrief.salesId = row.getInteger("salesId");
                buyerBrief.salesTel = row.getString("tel");
                buyerBrief.userId = row.getString("userId");
                Float money = conn.createQuery(moneySql)
                        .addParameter("buyerId", buyerBrief.userId)
                        .addParameter("startTime", pageBuilder.startTime)
                        .addParameter("endTime", pageBuilder.endTime)
                        .executeScalar(Float.class);
                buyerBrief.buyMoney = money == null ? 0 : money;
                buyerBriefs.add(buyerBrief);
            }
            AdminUsersResponse usersResponse = new AdminUsersResponse();
            usersResponse.buyers = buyerBriefs;
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
