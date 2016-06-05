package onefengma.demo.rx;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.sql2o.Connection;
import org.sql2o.Query;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
import onefengma.demo.server.core.LogUtils;
import onefengma.demo.server.core.PageBuilder;
import onefengma.demo.server.model.product.IronProduct;
import onefengma.demo.server.services.products.IronDataHelper;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class ReDemo {

    private String aaa = "fengma";
    private String bbb = "fengma1";
    private String ccc = "fengma2";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        String url = "%E4%B8%8D%E9%94%88%E9%92%A2%E7%AE%A1";

        System.out.println(URLDecoder.decode(url, "utf-8"));
    }

    protected  static  String createInsertSql(String table, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sqlBuilder = new StringBuffer("insert into " + table + "(");
        StringBuffer valueBuilder = new StringBuffer(" values (");
        for (Field field : fields) {
            sqlBuilder.append(field.getName());
            valueBuilder.append(":" + field.getName());
            if (fields[fields.length - 1] == field) {
                sqlBuilder.append(")");
                valueBuilder.append(")");
            } else {
                sqlBuilder.append(",");
                valueBuilder.append(",");
            }
        }
        sqlBuilder.append(valueBuilder);
        return sqlBuilder.toString();
    }

    public Query bind(Query query, Object bean){
        Class clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            try{
                method.setAccessible(true);
                String methodName = method.getName();
                /*
                It looks in the class for all the methods that start with get
                */
                if(methodName.startsWith("get") && method.getParameterTypes().length == 0){
                    String param = methodName.substring(3);//remove the get prefix
                    param = param.substring(0, 1).toLowerCase() + param.substring(1);//set the first letter in Lowercase => so getItem produces item
                    Object res = method.invoke(bean);
                    if( res!= null){
                        try {
                            Method addParam = this.getClass().getDeclaredMethod("addParameter", param.getClass(), method.getReturnType());
                            addParam.invoke(this, param, res);
                        } catch (NoSuchMethodException ex) {
                            LogUtils.e(ex, ex.getMessage());
                            query.addParameter(param, res);
                        }
                    }else
                        query.addParameter(param, res);
                }
            }catch(IllegalArgumentException ex){
                LogUtils.e(ex, ex.getMessage());
            }catch(IllegalAccessException ex){
                throw new RuntimeException(ex);
            } catch (SecurityException ex) {
                throw new RuntimeException(ex);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }
        return query;
    }

    public static class IronDatas {
        private String aaa = "FFFFF";

        @Override
        public String toString() {
            return aaa;
        }
    }


    public static class AsciiID {
        private static final String alphabet=
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        private int currentId;

        public String nextId() {
            int id = currentId++;
            StringBuilder b = new StringBuilder();
            do {
                b.append(alphabet.charAt(id % alphabet.length()));
            } while((id /=alphabet.length()) != 0);

            return b.toString();
        }
    }

    public String createSql(String sql) {
        if (StringUtils.isEmpty(sql)) {
            return "";
        }
        Pattern pattern = Pattern.compile("@\\w*");
        Matcher m = pattern.matcher(sql);
        try {
            while(m.find()) {
                String findStr = m.group();
                String filedStr = findStr.replace("@", "");
                Field field = this.getClass().getDeclaredField(filedStr);
                field.setAccessible(true);
                String value = field.get(this).toString();
                sql = sql.replaceAll(findStr, value);
            }
            return sql;
        } catch (Exception e) {
            LogUtils.e(e, e.getMessage());
        }
        return "";
    }


}
