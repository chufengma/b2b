package onefengma.demo.server.core;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import onefengma.demo.annotation.NotRequired;
import onefengma.demo.common.StringUtils;
import onefengma.demo.server.config.Config;
import onefengma.demo.server.config.Config.DataBaseModel;

/**
 * @author yfchu
 * @date 2016/5/23
 */
public abstract class BaseDataHelper {

    private static Sql2o sql2o;
    private static String table;

    public static Sql2o getSql2o() {
        if (sql2o == null) {
            DataBaseModel dataBaseModel = Config.instance().getDataBaseModel();
            sql2o = new Sql2o(dataBaseModel.getUrl(), dataBaseModel.getUser(), dataBaseModel.getPassword());
        }
        return sql2o;
    }

    public Connection getConn() {
        return getSql2o().open();
    }

    protected static String createInsetStr(String table, Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        StringBuffer sqlBuilder = new StringBuffer("insert into " + table + " ( ");
        StringBuffer valueBuilder = new StringBuffer(" values ( ");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(NotRequired.class)) {
                continue;
            }
            sqlBuilder.append(field.getName());
            valueBuilder.append(" :" + field.getName());
            sqlBuilder.append(',');
            valueBuilder.append(',');
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        sqlBuilder.append(")");
        valueBuilder.deleteCharAt(valueBuilder.length() - 1);
        valueBuilder.append(")");
        sqlBuilder.append(valueBuilder);
        System.out.println("----" + sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public int getMaxCounts(String table) {
        String sql = "select count(id) from " + table;
        try (Connection conn = getConn()) {
            int count = conn.createQuery(sql).executeScalar(Integer.class);
            return count;
        }
    }

    protected static Query createInsertQuery(Connection conn, String table, Object bean) throws IllegalAccessException, UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException {
        return bind(conn.createQuery(createInsetStr(table, bean).toString()), bean);
    }

    public static Query bind(Query query, Object bean) throws IllegalAccessException, UnsupportedEncodingException, InvocationTargetException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotRequired.class)) {
                continue;
            }
//            try {
//                Method addParam = query.getClass().getDeclaredMethod("addParameter", String.class, field.getType());
//                addParam.invoke(query, field.getName(), field.get(bean));
//            } catch (NoSuchMethodException e) {
//                LogUtils.e(e, e.getMessage());
//                query.addParameter(field.getName(), field.get(bean));
//            }
            if (field.getType() == String.class) {
                query.addParameter(field.getName(), (String) field.get(bean));
            } else {
                query.addParameter(field.getName(), field.get(bean));
            }
        }
        return query;
    }

    protected String createSql(String sql) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmpty(sql)) {
            return "";
        }
        Pattern pattern = Pattern.compile("@\\w*");
        Matcher m = pattern.matcher(sql);
        while (m.find()) {
            String findStr = m.group();
            String filedStr = findStr.replace("@", "");
            Field field = this.getClass().getDeclaredField(filedStr);
            field.setAccessible(true);
            String value = field.get(this).toString();
            sql = sql.replaceAll(findStr, value);
        }
        return sql;
    }

    public static String generateFiledString(Class clazz) {
        if (clazz == null) {
            return "";
        }
        Field fields[] = clazz.getFields();
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            stringBuilder.append(field.getName());
            if (i != fields.length -1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static String generateFiledStringExclude(Class clazz, String ...keys) {
        if (clazz == null) {
            return "";
        }
        Field fields[] = clazz.getFields();
        StringBuilder stringBuilder = new StringBuilder("");

        List<String> results = new ArrayList<>();

        FIND:
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (keys != null) {
                for(String key : keys) {
                    if (StringUtils.equals(field.getName(), key)) {
                        continue FIND;
                    }
                }
            }
            results.add(field.getName());
        }

        for(int i=0; i< results.size(); i++) {
            stringBuilder.append(results.get(i));
            if (i != results.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    protected void transaction(Func func) throws Exception {
        try(Connection conn = getSql2o().beginTransaction()) {
            try {
                func.doIt(conn);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public interface Func{
        public void doIt(Connection conn) throws Exception;
    }
}
