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
import onefengma.demo.server.services.funcs.CityDataHelper;
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

    public List<LogisticsNormalBean> getNewestLogisticsRequests(int maxCount) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(LogisticsNormalBean.class) + " from logistics_request order by pushTime desc limit " + maxCount;
        try(Connection conn = getConn()) {
            List<LogisticsNormalBean> beans = conn.createQuery(sql).executeAndFetch(LogisticsNormalBean.class);
            for(LogisticsNormalBean bean : beans) {
                bean.setStartCity(CityDataHelper.instance().getCityDescById(bean.startPoint));
                bean.setEndCity(CityDataHelper.instance().getCityDescById(bean.endPoint));
            }
            return beans;
        }
    }

    public LogisticsPageResponse getLogisticsRequests(PageBuilder pageBuilder) throws NoSuchFieldException, IllegalAccessException {
        String sql = "select " + generateFiledString(LogisticsNormalBean.class) + " from logistics_request " + pageBuilder.generateWherePlus(true) + " order by pushTime desc " + pageBuilder.generateLimit();
        String countSql = "select count(*) from logistics_request " + pageBuilder.generateWherePlus(true);
        try(Connection conn = getConn()) {
            LogisticsPageResponse response = new LogisticsPageResponse(pageBuilder.currentPage, pageBuilder.pageCount);
            Integer integer = conn.createQuery(countSql).executeScalar(Integer.class);
            response.maxCount = integer == null ? 0 : integer;
            response.logisticsRequest = conn.createQuery(sql).executeAndFetch(LogisticsNormalBean.class);
            for(LogisticsNormalBean bean : response.logisticsRequest) {
                bean.setStartCity(CityDataHelper.instance().getCityDescById(bean.startPoint));
                bean.setEndCity(CityDataHelper.instance().getCityDescById(bean.endPoint));
            }
            return response;
        }
    }

    public LogisticsNormalBean getLogisticsById(String id) {
        String sql = "select " + generateFiledString(LogisticsNormalBean.class) + " from logistics_request where id=:id";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("id", id).executeAndFetchFirst(LogisticsNormalBean.class);
        }
    }

    public void upodateLogisticsStatusById(String id, int status) {
        String sql = "update logistics_request set status=:status where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).addParameter("status", status).executeUpdate();
        }
    }

    public void deleteLogisticsById(String id) {
        String sql = "delete from logistics_request where id=:id";
        try(Connection conn = getConn()) {
            conn.createQuery(sql).addParameter("id", id).executeUpdate();
        }
    }

    public static class Good {
        public String name;
        public double count;
    }
}
