package com.litong.utils.date;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by litong on 2018/8/22 0022.
 * 处理时间的工具类
 */
public class DateUtil {
  /**
   * 年,月,日
   */
  private static SimpleDateFormat sdfDate = null;

  /**
   * 存储 SimpleDateForamt的格式
   */
  Map<String, SimpleDateFormat> sdfMaps = new HashMap<>();

  static {
    sdfDate = new SimpleDateFormat("yyyy-MM-dd");
  }

  /**
   * 返回年月日
   * @param date yyyy-MM-dd HH:mm:ss
   */
  public static String getyyyy_MM_ddString(Date date) {
    String str = sdfDate.format(date);
    return str;
  }

  public static Date getyyyy_MM_dd(Date date) {
    String str = getyyyy_MM_ddString(date);
    Date d = null;
    try {
      d = sdfDate.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return d;
  }

  /**
   * 获取N天前的日期,如果传入10表示获取10天后的日期,如果传入-10,表示获取10天前的日期
   */
  public static Date getDayAgo(int i) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_YEAR, i);
    Date time = calendar.getTime();
    return time;
  }

  /**
   * java.lang.String ==> java.util.Date
   * @throws ParseException 
   */
  public Date toDate(String str, String format) throws ParseException {
    SimpleDateFormat sdf = getSimpleDateFormat(format);
    Date parse = sdf.parse(str);
    return parse;
  }

  /**
   * java.util.Date==>java.lang.String
   */
  public String toString(Date date, String format) {
    SimpleDateFormat sdf = getSimpleDateFormat(format);
    String string = sdf.format(date);
    return string;
  }

  /**
   * 如果有sdf从map中取,如果没有sdf,new后,放到map中
   * @param format
   * @return
   */
  private SimpleDateFormat getSimpleDateFormat(String format) {
    SimpleDateFormat sdf = sdfMaps.get(format);
    if (sdf == null) {
      sdf = new SimpleDateFormat(format);
      sdfMaps.put(format, sdf);
    }
    return sdf;
  }

  /**
   * 判断d3是否在d1和d2之间
   * @param d1
   * @param d2
   * @param d3
   * @return
   */
  public static boolean isBetween(Date d1, Date d2, Date d3) {
    Calendar instance = Calendar.getInstance();
    instance.setTime(d1);
    long l1 = instance.getTimeInMillis();
    instance.setTime(d2);
    long l2 = instance.getTimeInMillis();
    instance.setTime(d3);
    long l3 = instance.getTimeInMillis();
    if (l3 > l1 && l3 < l2) {
      return true;
    }
    return false;
  }

  /**
   * 获取 java.util.Date中日期部分
   * @param date
   * @return
   */
  public static Date getDate(Date date) {
    // long time = date.getTime();
    return null;
  }
  
  public static Date getFirstDayOfMonth() {
    Calendar cal = Calendar.getInstance();
    // 获取某月最小天数
    int firstDay = cal.getMinimum(Calendar.DATE);
    // 设置日历中月份的最小天数
    cal.set(Calendar.DAY_OF_MONTH, firstDay);
    return cal.getTime();
  }

  /**
   * 获取指定年月的第一天
   * @param year
   * @param month
   * @return
   */
  public static Date getFirstDayOfMonth(int year, int month) {
    Calendar cal = Calendar.getInstance();
    // 设置年份
    cal.set(Calendar.YEAR, year);
    // 设置月份
    cal.set(Calendar.MONTH, month - 1);
    // 获取某月最小天数
    int firstDay = cal.getMinimum(Calendar.DATE);
    // 设置日历中月份的最小天数
    cal.set(Calendar.DAY_OF_MONTH, firstDay);
    return cal.getTime();
  }

  public static Date getFirstDayOfYear(Date year) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(year);
    calendar.set(calendar.get(Calendar.YEAR), 0, 1);
    return calendar.getTime();
  }

  private final static String HYPHEN = "-";

  /**
   * 默认失效时间9999-12-31
   */
  public static BigDecimal getDefaultInvalidTime() {
    // 获取String类型的时间
    String defaultInvalidTime = "9999-12-31";
    return strToBigDecimal(defaultInvalidTime);
  }

  /**
   * 默认生效时间 为当前时间
   */
  public static BigDecimal getNowDate() {
    // 我要获取当前的日期
    Date date = new Date();
    // 设置要获取到什么样的时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 获取String类型的时间
    String createdate = sdf.format(date);
    return strToBigDecimal(createdate);
  }

  /**
   * 生效日期，失效日期 String->BigDecimal
   * 
   * @param effectiveDate
   * @return BigDecimal
   */
  public static BigDecimal strToBigDecimal(String effectiveDate) {
    StringBuilder sb = new StringBuilder();
    String[] splitWithHyphen = effectiveDate.split(HYPHEN);
    for (String item : splitWithHyphen) {
      sb.append(item);
    }
    String newEffectiveDate = sb.toString();
    BigDecimal bd = new BigDecimal(newEffectiveDate);
    return bd;
  }

  /**
   * 生效日期，失效日期 BigDecimal->String
   * 
   * @param effectiveDate
   * @return String
   */
  public static String bigDecimalToStr(BigDecimal effectiveDate) {
    String strEffectiveDate = effectiveDate.toString();
    StringBuilder sb = new StringBuilder();
    sb.append(strEffectiveDate);
    sb.insert(4, HYPHEN);
    sb.insert(7, HYPHEN);
    return sb.toString();
  }

  public static Date getyyyy_MM_dd() {
    Date getyyyy_MM_dd = getyyyy_MM_dd(new Date());
    return getyyyy_MM_dd;
  }
}
