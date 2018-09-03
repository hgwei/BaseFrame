package com.hgw.baseframe.util;

import android.content.Context;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 描述：常用时间方法类
 * @author hgw
 * 按照序号，方法目录如下（添加新方法，序号自增）：
 * 1、描述：获取时间戳，毫秒
 * 2、描述：获取时间戳，秒
 * 3、描述：将时间戮(秒)转成日期型字符串
 * 3.1、描述：将时间戮(毫秒)转成日期型字符串
 * 4、描述：格式yyyy-MM-dd HH:mm:ss转时间戳
 * 5、描述：格式yyyy-MM-dd HH:mm:ss:SSS转时间戳
 * 6、描述：生成版权日期字符
 * 7、描述：获取现在时间（返回Date类型）
 * 8、描述：获取现在时间（返回String类型）
 * 9、描述：String转换为Date
 * 10、描述：Date转为String
 * 11、描述：提取一个月中的最后一天
 * 12、描述：得到现在小时
 * 13、描述：得到现在分钟
 * 14、描述：二个小时时间间的差值
 * 15、描述：得到二个日期间的间隔天数
 * 16、描述：得到一个时间延后或前移几天的时间
 * 17、描述：判断是否润年
 * 18、描述：返回美国时间格式 26 Apr 2006
 * 19、描述：获取一个月的最后一天
 * 20、描述：判断二个时间是否在同一个周
 * 21、描述：产生周序列,即得到当前时间所在的年度是第几周
 * 22、描述：获得一个日期所在的周的星期几的日期
 * 25、描述：两个时间之间的天数
 * 26、描述：判断今天是周几
 * 27、描述：根据时间戳获取相对时间描述（例如：1分钟前、1个小时前）
 * 29、描述：默认Locale.CHINA的SimpleDateFormat内部类
 * 30、描述：根据时间戳（毫秒）获取小时数
 * 31、描述：根据时间戳（毫秒）获取分钟数
 * 32、描述：根据时间戳（毫秒）获取秒数
 * 33、描述：比较两个日期的大小
 * 34、描述：获取昨天的日期
 * 35、描述：得到当年当月的最大日期
 * 36、描述：获取前n天日期、后n天日期
 * 37、描述：消息时间规则
 *
 */
public class TimeUtil {

    /**1、描述：获取时间戳，毫秒*/
    public static long getTimestampMS(Context context) {
        return System.currentTimeMillis();
    }

    /**2、描述：获取时间戳，秒*/
    public static long getTimestampSec() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 3、描述：将时间戮(秒)转成日期型字符串
     * @param style：返回日期的格式（可以传null）
     * @param datestr：时间戮 (秒)
     * @return
     */
    public static String getFormatDate(String style, long datestr) {
        String date = "";
        if (style == null || "".equalsIgnoreCase(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINA);
        Date d = new Date(datestr*1000);
        date = format.format(d);
        return date;
    }
    
    /**
     * 3.1、描述：将时间戮(毫秒)转成日期型字符串
     * @param style：返回日期的格式（可以传null）
     * @param datestr：时间戮 (毫秒)
     * @return
     */
    public static String getFormatDate2(String style, long datestr) {
        String date = "";
        if (style == null || "".equalsIgnoreCase(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(style, Locale.CHINA);
        Date d = new Date(datestr);
        date = format.format(d);
        return date;
    }

    /**
     * 4、描述：格式yyyy-MM-dd HH:mm:ss转时间戳
     * @param context
     * @param str: 日期型字符串
     * @return
     */
    public static long getTimestampFromString(Context context, String str) {
        if (str == null || str.equals("")) {
            return getTimestampMS(context);
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = null;// 用于时间转化
        try {
            date = f.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimestampMS(context);
    }
    /**
     * 4.1、描述：格式yyyy-MM-dd HH:mm:ss转时间戳
     * @param context
     * @param str: 日期型字符串
     * @param style: 格式
     * @return
     */
    public static String getTimestampFromString(Context context, String str, String style) {
        if (str == null || str.equals("")) {
            return getFormatDate(style,getTimestampMS(context));
        }
        if (style == null || "".equalsIgnoreCase(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat f = new SimpleDateFormat(style, Locale.CHINA);
        Date date = null;// 用于时间转化
        try {
            date = f.parse(str);
            return getFormatDate(style,date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getFormatDate(style,getTimestampMS(context));
    }


    /**
     *5、描述：格式yyyy-MM-dd HH:mm:ss:SSS转时间戳
     * @param context
     * @param str
     * @return
     */
    public static long getTimestampFromStringMillis(Context context, String str) {
        if (str == null || str.equals("")) {
            return getTimestampMS(context);
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA);
        Date date = null;// 用于时间转化
        try {
            date = f.parse(str);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimestampMS(context);
    }

    /**
     * 6、描述：生成版权日期字符
     * @param byear
     * @return
     */
    public static String getCopyRight(int byear) {
        String str = "Copyright © 1997–2014";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        str = "Copyright © " + byear + "–" + year;
        return str;
    }

    /**
     * 7、描述：获取现在时间（返回Date类型）
     * @param style :要获取时间格式类型，传null就默认格式（例如：yyyy-MM-dd HH:mm:ss）
     * @return style
     */
    public static Date getNowDate(String style) {
        Date currentTime = new Date();
        if (style == null || "".equalsIgnoreCase(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat(style);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        return formatter.parse(dateString, pos);
    }

    /**
     * 8、描述：获取现在时间（返回String类型）
     *  @param style: 要获取时间格式类型，传null就默认格式（例如：yyyy-MM-dd HH:mm:ss）
     * @return style
     */
    public static String getStringDate(String style) {
        Date currentTime = new Date();
        if (style == null || "".equalsIgnoreCase(style)) {
            style = "yyyy-MM-dd HH:mm:ss";
        }
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat(style);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 9、描述：String转换为Date
     * @param strDate
     * @param format： 要获取的时间格式类型（例如：yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static Date strToDate(String strDate, String format) {
        if (TextUtils.isEmpty(strDate)) return new Date();
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 10、描述：Date转为String
     * @param dateDate
     * @param format： 要获取的时间格式类型（例如：yyyy-MM-dd HH:mm:ss）
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateToStr(Date dateDate, String format) {
    	if (format == null || "".equalsIgnoreCase(format)) {
    		format = "yyyy-MM-dd HH:mm:ss";
        }
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 11、描述：提取一个月中的最后一天
     * @param day
     * @return
     */
    public static Date getLastDate(long day) {
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        return date_3_hm_date;
    }

    /**
     * 12、描述：得到现在小时
     * @return
     */
    public static String getHour() {
        Date currentTime = new Date();
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 13、描述：得到现在分钟
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 14、描述：二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     * @param st1
     * @param st2
     * @return
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 15、描述：得到二个日期间的间隔天数
     * @param sj1
     * @param sj2
     * @return
     */
    public static String getTwoDay(String sj1, String sj2) {
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = formatter.parse(sj1);
            Date mydate = formatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 16、描述：得到一个时间延后或前移几天的时间
     * @param nowdate 参考时间
     * @param delay 前移或后延的天数
     * @return
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate,"yyyy-MM-dd");
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = formatter.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 17、描述：判断是否润年
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计：</br> 1.被400整除是闰年 </br> 否则：</br> 2.不能被4整除则不是闰年 </br> 3.能被4整除同时不能被100整除则是闰年 </br> 3.能被4整除同时能被100整除则不是闰年 </br>
         */
        Date d = strToDate(ddate,"yyyy-MM-dd");
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            return (year % 100) != 0;
        } else
            return false;
    }

    /**
     * 18、描述：返回美国时间格式 26 Apr 2006
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase(Locale.CHINA) + k[5].substring(2, 4);
    }

    /**
     * 19、描述：获取一个月的最后一天
     * @param dat
     * @return
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 20、描述：判断二个时间是否在同一个周
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 21、描述：产生周序列,即得到当前时间所在的年度是第几周
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * 22、描述：获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     * @param sdate
     * @param num
     * @return
     */
    public static String getWeek(String sdate, String num) {
        // 再转换为时间
        Date dd = strToDate(sdate,"yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        else if (num.equals("2")) // 返回星期二所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        else if (num.equals("3")) // 返回星期三所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        else if (num.equals("4")) // 返回星期四所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        else if (num.equals("5")) // 返回星期五所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        else if (num.equals("6")) // 返回星期六所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        else if (num.equals("0")) // 返回星期日所在的日期
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new DefaultLocaleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 25、描述：两个时间之间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        DefaultLocaleDateFormat formatter = new DefaultLocaleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = formatter.parse(date1);
            mydate = formatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 26、描述：判断今天是周几
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 26、描述：判断今天是周几
     * @param dt
     * @return
     */
    public static String getWeekOfDate2(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 27、描述：根据时间戳获取相对时间描述（例如：1分钟前、1个小时前） 
     * @param timestampInSec-- 单位为秒的时间戳
     */
    public static String getDistanceTime(long timestampInSec) {
        // Calendar.get(Calendar.DATE)
        Date now = new Date();
        long day = 0; // 天数
        long hour = 0; // 小时
        long min = 0; // 分钟
        @SuppressWarnings("unused")
        long sec = 0; // 秒
        try {
            long curTimestampInMillisec = now.getTime();
            timestampInSec = timestampInSec * 1000l;
            long diff;
            if (curTimestampInMillisec < timestampInSec) {
                diff = timestampInSec - curTimestampInMillisec;
            } else {
                diff = curTimestampInMillisec - timestampInSec;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000));
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rs = "";
        if (hour == 0) {
            rs = min + "分钟前";
            return rs;
        }
        if (day == 0 && hour <= 4) {
            rs = hour + "小时前";
            return rs;
        }
        DefaultLocaleDateFormat format = new DefaultLocaleDateFormat("MM-dd HH:mm");
        String d = format.format(timestampInSec);
        Date date = null;
        try {
            // 把字符类型的转换成日期类型的！
            date = format.parse(d);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        if (now.getDate() - date.getDate() == 0) {
            // 当前时间和时间戳转换来的时间的天数对比
            DateFormat df2 = new DefaultLocaleDateFormat("HH:mm");
            rs = "今天  " + df2.format(timestampInSec);
            return rs;
        } else if (now.getDate() - date.getDate() == 1) {
            DateFormat df2 = new DefaultLocaleDateFormat("HH:mm");
            rs = "昨天  " + df2.format(timestampInSec);
            return rs;
        } else {
            DateFormat df2 = new DefaultLocaleDateFormat("MM-dd HH:mm");
            rs = df2.format(timestampInSec);
            return rs;
        }
    }

    /**
     * 29、描述：默认Locale.CHINA的SimpleDateFormat内部类
     * 
     * @author hgw
     * 
     */
    private static class DefaultLocaleDateFormat extends SimpleDateFormat {
        private static final long serialVersionUID = -2496520038803137265L;

        public DefaultLocaleDateFormat(String format) {
            // zh_CN
            super(format, Locale.CHINA);
        }
    }

    /**
     * 30、描述：根据时间戳（毫秒）获取小时数
     * @param ms
     * @return
     */
    public static String getHH(long ms) {
        long hh = ms / (60 * 60 * 1000);
        hh = hh < 0 ? 0 : hh;
        
        return hh < 10 ? "0" + hh : "" + hh;
    }

    /**
     *31、描述：根据时间戳（毫秒）获取分钟数
     * @param ms
     * @return
     */
    public static String getMM(long ms) {
        long mm = (ms % (60 * 60 * 1000)) / (60 * 1000);
        mm = mm < 0 ? 0 : mm;
        
        return mm < 10 ? "0" + mm : "" + mm;
    }

    /**
     * 32、描述：根据时间戳（毫秒）获取秒数
     * @param ms
     * @return
     */
    public static String getSS(long ms) {
        long ss = (ms % (60 * 1000)) / 1000;
        ss = ss < 0 ? 0 : ss;
        
        return ss < 10 ? "0" + ss : "" + ss;
    }
    
    /**
     * 33、描述：比较两个日期的大小
     * @return 1（DATE1>DATE2）、-1（DATE1<DATE2）、0（DATE1=DATE2）
     */
    public static int compareDate(String DATE1, String DATE2) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 34、描述：获取昨天的日期
     * @param style: 要获取时间格式类型，传null就默认格式（例如：yyyy-MM-dd HH:mm:ss）
     * @return style
     * */
    public static String yesterdayDate(String style){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat(style).format(cal.getTime());
        return yesterday;
    }

    /**
     * 35、描述：得到当年当月的最大日期
     * @param year: 年份
     * @param year: 月份
     * @return day：当月的天数
     * */
    public static int MaxDayFromDay_OF_MONTH(int year,int month){
        Calendar time= Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    /**
     * 36、描述：获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     *                    例如：今天：getOldDate(0)；昨天：getOldDate(-1)；明天：getOldDate(1)
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 36.1、描述：获取前n天日期、后n天日期
     *@param  startTime 起始时间
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     *                    例如：今天：getOldDate(0)；昨天：getOldDate(-1)；明天：getOldDate(1)
     * @return
     */
    public static String getOldDate2(String startTime, int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        try {
            beginDate = dft.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date beginDate = new Date(startTime);
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }


    /**
     * 消息时间规则
     * 当天（24小时内） 00:00~23:59
     * 昨天（上一个自然日） 昨天
     * 超过48小时  星期天至星期五
     * 超过一周 年.月.日，例如：2017.01.12
     * */
    public static String getDateDecs(String timeString){
        if(TextUtils.isEmpty(timeString)){
            return "";
        }
        String currentTime= TimeUtil.getStringDate("yyyy-MM-dd");
        String distanceDay=TimeUtil.getTwoDay(currentTime,timeString.substring(0,10));

        if(Integer.valueOf(distanceDay)==0){
            return timeString.substring(11,16);
        }else if(Integer.valueOf(distanceDay)==1){
            return "昨天";
        }else if(Integer.valueOf(distanceDay)>=2 && Integer.valueOf(distanceDay)<=7){
            return TimeUtil.getWeekOfDate(TimeUtil.strToDate(timeString,"yyyy-MM-dd HH:mm:ss"));
        }else{
            return timeString.substring(0,10).replace("-",".");
        }
    }

    //计算指定时间后的日期
    public static String getAfterDate(String nowDate, long day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(nowDate,"yyyy-MM-dd HH:mm:ss"));
        calendar.setTimeInMillis(calendar.getTimeInMillis()+day*24L*60L*60L*1000L);//计算单位是毫秒
        return dateToStr(calendar.getTime(),"yyyy-MM-dd HH:mm:ss");
    }

    //返回指定格式的时间段，这个方法仅仅是为了方便分享赛果界面ShareChallengeResultActivity的时间段展示
    public static String getTargetDate(String nowDate, long day){
        if (TextUtils.isEmpty(nowDate)) {
            nowDate = dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        }
        String endDate = getAfterDate(nowDate,day);
        String newStr = nowDate.substring(5,7)+"."+nowDate.substring(8,10)+"~"+endDate.substring(5,7)+"."+endDate.substring(8,10);
        return newStr;
    }

    //判断当前时间是否在指定时间之后(几天之后)
    public static boolean afterTargetDate(String targetDate, long day){
        long nowTime = System.currentTimeMillis();
        long targetTime = strToDate(targetDate,"yyyy-MM-dd").getTime();
        long oneDay = day*24L*60L*60L*1000L;
        return nowTime >= (targetTime+oneDay);
    }
}