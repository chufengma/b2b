package onefengma.demo.common;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean equals(String str, String str2) {
        return str == null ? false : str.equals(str2);
    }

    public static boolean equalsIngcase(String str, String str2) {
        return str == null ? false : str.toLowerCase().equals(str2.toLowerCase());
    }

}
