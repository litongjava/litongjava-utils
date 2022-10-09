package com.litongjava.utils.localdate;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * @author litongjava@qq.com on 9/22/2022 1:03 AM
 */
public class JavaLocalDateUtils {
  public static LocalDate firstDayOfYearMonth(Integer year, Integer month) {
    return LocalDate.of(Integer.valueOf(year), Integer.valueOf(month), 1);
  }

  public static LocalDate lastDayOfYearMonth(int year, int month) {
    LocalDate firstDayOfCurrentDate = LocalDate.of(year, month, 1);
    return firstDayOfCurrentDate.with(TemporalAdjusters.lastDayOfMonth());
  }

  public static LocalDate firstDayOfYear(Integer year) {
    return LocalDate.of(Integer.valueOf(year), 1, 1);
  }

  public static LocalDate lastDayOfYear(Integer year) {
    return LocalDate.of(Integer.valueOf(year), 12, 31);
  }

}
