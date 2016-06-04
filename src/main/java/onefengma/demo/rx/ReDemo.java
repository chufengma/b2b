package onefengma.demo.rx;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import onefengma.demo.common.DateHelper;
import onefengma.demo.common.StringUtils;
import onefengma.demo.common.VerifyUtils;
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
        System.out.println(VerifyUtils.isMobile("18355551276"));
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
