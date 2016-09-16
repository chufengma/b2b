package onefengma.demo.server.services.funcs;

import org.sql2o.Connection;

import onefengma.demo.server.core.BaseDataHelper;

/**
 * @author yfchu
 * @date 2016/9/9
 */
public class DownloadDataHelper extends BaseDataHelper {

    private static DownloadDataHelper instance;

    public static DownloadDataHelper instance() {
        if (instance == null) {
            instance = new DownloadDataHelper();
        }
        return instance;
    }

    public void addAppDownloadTimes(String appName) {
        String insertSql = "insert into app_download set times=:times, appName=:appName";
        String updateSql = "update app_download set times=:times where appName=:appName";

        AppDownloadInfo info = getAppDownLoadInfo(appName);
        try(Connection conn = getConn()) {
            if (info == null) {
                conn.createQuery(insertSql).addParameter("times", 1).addParameter("appName", appName).executeUpdate();
            } else {
                conn.createQuery(updateSql).addParameter("times", info.times + 1).addParameter("appName", appName).executeUpdate();
            }
        }
    }

    public AppDownloadInfo getAppDownLoadInfo(String appName) {
        String sql = "select " + generateFiledString(AppDownloadInfo.class) + " from app_download where appName=:appName";
        try(Connection conn = getConn()) {
            return conn.createQuery(sql).addParameter("appName", appName).executeAndFetchFirst(AppDownloadInfo.class);
        }
    }

    public static class AppDownloadInfo {
        public String appName;
        public int times;
    }

}
