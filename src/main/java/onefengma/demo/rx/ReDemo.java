package onefengma.demo.rx;

import org.eclipse.jetty.http.MimeTypes;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimeTypeParameterList;
import javax.activation.MimetypesFileTypeMap;

import onefengma.demo.common.StringUtils;
import onefengma.demo.server.core.LogUtils;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class ReDemo {

    private String aaa = "fengma";
    private String bbb = "fengma1";
    private String ccc = "fengma2";

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//        MimetypesFileTypeMap.setDefaultFileTypeMap();
//        System.out.println(MimeTypeParameterList.);

        String sql = "select (@aaa, @bbb, @ccc) from user (@ccc)";
        ReDemo reDemo = new ReDemo();
        System.out.println(reDemo.createSql(sql));
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
