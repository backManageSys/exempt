package njurestaurant.njutakeout.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class FormatDateTime {

    public static String toLongDateString(Date dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        return myFmt.format(dt);
    }

    public static String toShortDateString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        return myFmt.format(dt);
    }

    public static String toShortDateString(Date dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        return myFmt.format(dt);
    }

    public static Date fromShortDateString(String dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        try {
            return myFmt.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocalDate fromDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDate();
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toLongTimeString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("HH mm ss SSSS");
        return myFmt.format(dt);
    }

    public static String toShortTimeString(Date dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yy/MM/dd");
        return myFmt.format(dt);
    }

    public static String toShortTimeString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yy/MM/dd");
        return myFmt.format(dt);
    }

    /**
     * Get date string used in bill (yyyyMMdd).
     *
     * @return date string in yyyyMMdd
     */
    public static String currentDateStringForBill() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
        return myFmt.format(dt);
    }

    public static String currentDateString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月");
        return myFmt.format(dt);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 10位时间戳转换成date
     * @param time 10位时间戳
     * @return
     */
    public static Date TenTimestampToDate(Integer time){
        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 13位时间戳转换成date
     * @param time 13位时间戳
     * @return
     */
    public static Date ThirdTimestampToDate(Long time){
        Timestamp ts = new Timestamp(time);
        Date date = new Date();
        try {
            date = ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断的d1日期是否在d2之前/为同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isDayBeforeOrEqualThan(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        if (c1.get(Calendar.YEAR) < c2.get(Calendar.YEAR)) {
            return true;
        } else if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            if(c1.get(Calendar.MONTH) < c2.get(Calendar.MONTH)) return true;
            else if(c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
                return c1.get(Calendar.DAY_OF_MONTH) <= c2.get(Calendar.DAY_OF_MONTH);
            } else return false;
        } else return false;
    }
}
