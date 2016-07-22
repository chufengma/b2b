package onefengma.demo.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getDataStr(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(time);
    }

    public static String getDataStrWithOut() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    public static String getDayStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        return dateFormat.format(new Date());
    }

    public static long getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static long getLastDayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis() - 24 * 60 * 60 * 1000;
    }

    public static long getNextDayTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis() + 24 * 60 * 60 * 1000;
    }

    public static long getThisMonthStartTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    public static long getLastMonthStartTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTimeInMillis();
    }


    public static long getNextMonthStatimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTimeInMillis();
    }

    public static boolean isToday(long time) {
        return time < getLastDayTimestamp() && time >= getLastDayTimestamp();
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static TimeRange getMonthTimeRange(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        TimeRange timeRange = new TimeRange();
        timeRange.startTime = calendar.getTimeInMillis();
        calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1) % 12, 1, 0, 0, 0);
        timeRange.endTime = calendar.getTimeInMillis();
        return timeRange;
    }

    public static class TimeRange {
        public long startTime;
        public long endTime;
    }


}
