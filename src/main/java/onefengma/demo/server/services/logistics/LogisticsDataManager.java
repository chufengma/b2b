package onefengma.demo.server.services.logistics;

import com.alibaba.fastjson.JSON;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.BaseDataHelper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.apibeans.logistics.LogisticsPageResponse;
import onefengma.demo.server.model.logistics.LogisticsNormalBean;
import org.sql2o.Connection;
import org.sql2o.logging.SysOutLogger;


/**
 * Created by chufengma on 2017/2/18.
 */
public class LogisticsDataManager extends BaseDataHelper {

    static LogisticsDataManager logisticsDataManager;

    public static LogisticsDataManager instance() {
        if (logisticsDataManager == null) {
            logisticsDataManager = new LogisticsDataManager();
        }
        return logisticsDataManager;
    }

    public void insertLogisticsRequest(LogisticsNormalBean logisticsNormalBean) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {
        try(Connection conn = getConn()) {
            createInsertQuery(conn, "logistics_request", logisticsNormalBean).executeUpdate();
        }
    }

    public LogisticsPageResponse getLogisticsRequests(PageBuilder pageBuilder) {
        String sql = "select " + generateFiledString(LogisticsNormalBean.class) + " from logistics_request " + pageBuilder.generateWherePlus(true) + " order by pushTime desc " + pageBuilder.generateLimit();
        String countSql = "select count(*) from logistics_request " + pageBuilder.generateWherePlus(true);
        try(Connection conn = getConn()) {
            LogisticsPageResponse response = new LogisticsPageResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            Integer integer = conn.createQuery(countSql).executeScalar(Integer.class);
            response.maxCount = integer == null ? 0 : integer;
            response.logisticsRequest = conn.createQuery(sql).executeAndFetch(LogisticsNormalBean.class);
            return response;
        }
    }

}
