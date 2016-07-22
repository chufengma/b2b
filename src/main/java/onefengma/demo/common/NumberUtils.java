package onefengma.demo.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author yfchu
 * @date 2016/5/24
 */
public class NumberUtils {

    public static float round(float number, int count) {
        BigDecimal bd = new BigDecimal(number + "");
        return bd.setScale(count, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
