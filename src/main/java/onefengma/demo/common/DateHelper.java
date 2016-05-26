package onefengma.demo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yfchu
 * @date 2016/5/26
 */
public class DateHelper {

    public static String getDataStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

}
