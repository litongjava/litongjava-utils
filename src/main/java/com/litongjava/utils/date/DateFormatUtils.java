package com.litongjava.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author litong
 * @date 2018-7-6
 *
 */
public class DateFormatUtils {

  private static String pattern1 = "yyy-MM-dd";
  private static String pattern2 = "yyy-MM-dd HH:mm:ss";

  public static Date parseToDate(String string) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
    Date parse = parse(string, simpleDateFormat);
    return parse;
  }

  public static Date parseToDateTime(String string) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern2);
    return parse(string, simpleDateFormat);
  }

  private static Date parse(String string, SimpleDateFormat simpleDateFormat) {
    Date parse = null;
    try {
      parse = simpleDateFormat.parse(string);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return parse;
  }

  public static Date parseToDate(String dateString, String datePattern) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    Date parse = parse(dateString, simpleDateFormat);
    return parse;
  }

  public static String format(Date date, String datePattern) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
    String dateString = simpleDateFormat.format(date);
    return dateString;
  }
}