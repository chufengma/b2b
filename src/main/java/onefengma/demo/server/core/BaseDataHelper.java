package onefengma.demo.server.core;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import onefengma.demo.server.config.Config;
import onefengma.demo.server.config.Config.DataBaseModel;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public abstract class BaseDataHelper {

    private static Sql2o sql2o;
    private static String table;

    protected static Sql2o getSql2o() {
        if (sql2o == null) {
            DataBaseModel dataBaseModel = Config.instance().getDataBaseModel();
            sql2o = new Sql2o(dataBaseModel.getUrl(), dataBaseModel.getUser(), dataBaseModel.getPassword());
        }
        return sql2o;
    }

    protected Connection getConn() {
        return getSql2o().open();
    }

}
