package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;
import org.sql2o.data.Row;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.recruit.IndexRecruit;
import onefengma.demo.server.model.recruit.RecruitResponse;

/**
 * Created by chufengma on 16/6/18.
 */
public class RecruitDataManager extends BaseDataHelper {

    private static RecruitDataManager recruitDataManager;

    public static RecruitDataManager instance() {
        if (recruitDataManager == null) {
            recruitDataManager = new RecruitDataManager();
        }
        return recruitDataManager;
    }

    public List<IndexRecruit> getIndexRecruits() {
        String sql = "select * from recruit order by pushTime des limit 0,10 ";
        try(Connection connection = getConn()) {
            List<Row> rows = connection.createQuery(sql).executeAndFetchTable().rows();
            List<IndexRecruit> recruits = new ArrayList<>();
            for (Row row : rows) {
                IndexRecruit recruit = new IndexRecruit();
                recruit.id = row.getString("id");
                recruit.title = row.getString("companyName") + " " + row.getString("position");
                recruit.pushTime = row.getLong("pushTime");
                recruits.add(recruit);
            }
            return recruits;
        }
    }

    public RecruitResponse getRecruitBriefs(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(RecruitBrief.class) + " from recruit order by pushTime des " + pageBuilder.generateLimit();
        String countSql = "select count(*) from recruit";
        RecruitResponse recruitResponse = new RecruitResponse(pageBuilder.currentPage, pageBuilder.pageCount);
        try(Connection connection = getConn()) {
            recruitResponse.recruits = connection.createQuery(sql).executeAndFetch(RecruitBrief.class);
            Integer count = connection.createQuery(countSql).executeScalar(Integer.class);
            recruitResponse.maxCount = count == null ? 0 : count;
        }
        return recruitResponse;
    }

    public RecruitDetail getRecruitDetail(String id) {
        String sql = "select " + generateFiledString(RecruitDetail.class) + " from recruit where id=:id";
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).addParameter("id", id).executeAndFetchFirst(RecruitDetail.class);
        }
    }

    public void deleteRecruit(String id) {
        String sql = "delete from recruit where id=:id";
        try (Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public void pushRecruit(RecruitDetail recruitDetail) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try (Connection conn = getConn()) {
            createInsertQuery(conn, "recruit", recruitDetail).executeUpdate();
        }
    }

    public void editRecruit(RecruitDetail recruitDetail) {
        String sql = "update recruit set companyName=:companyName, " +
                "place=:place, tel=:tel, position=:position, salary=:salary, welfare=:welfare, description=:description,pushTime=:pushTime where id=:id  ";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("companyName", recruitDetail.companyName)
            .addParameter("place", recruitDetail.place)
            .addParameter("tel", recruitDetail.tel)
            .addParameter("position", recruitDetail.position)
            .addParameter("salary", recruitDetail.salary)
            .addParameter("welfare", recruitDetail.welfare)
            .addParameter("description", recruitDetail.description)
            .addParameter("pushTime", System.currentTimeMillis())
            .addParameter("id", recruitDetail.id).executeUpdate();
        }
    }

    public static class RecruitBrief {
        public String id;
        public long pushTime;
        public String companyName;
        public String place;
        public String tel;
        public String position;
    }

    public static class RecruitDetail {
        public String id;
        public long pushTime;
        public String companyName;
        public String place;
        public String tel;
        public String position;
        public String salary;
        public String welfare;
        public String description;
    }

}
