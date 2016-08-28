package onefengma.demo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
        return (str == null || str2 == null) ? false : str.toLowerCase().equals(str2.toLowerCase());
    }

    public static String urlDecodeStr(String urlStr) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlStr, "utf-8");
    }

    public static String transArray(String[] arrays) {
        if (arrays == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrays.length; i++) {
            stringBuilder.append(arrays[i]);
            if (i != arrays.length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

}
