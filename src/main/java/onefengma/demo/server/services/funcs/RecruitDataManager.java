package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import java.util.List;

import onefengma.demo.server.core.BaseDataHelper;
import onefengma.demo.server.model.news.InnerNews;
import onefengma.demo.server.model.recruit.Recruit;

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

    public List<Recruit> getRecruits() {
        String sql = "select " + generateFiledString(Recruit.class) + " from recruit order by pushTime desc limit 0,10 ";
        try(Connection connection = getConn()) {
            return connection.createQuery(sql).executeAndFetch(Recruit.class);
        }
    }

}
