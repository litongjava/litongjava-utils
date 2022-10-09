package com.litongjava.utils.bigdecimal;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author litongjava@qq.com on 9/29/2022 9:33 AM
 * 进行单位进行单位转换
 */
public class BigDecimalUnitUtils {
  /**
   * 转转为万为单位,保留2为小数
   */
  public static BigDecimal to10K(BigDecimal input) {
    return input.divide(BigDecimal.valueOf(1e4), 2, BigDecimal.ROUND_HALF_UP);
  }


  /**
   * 转为亿元,保留两位小数
   */
  public static BigDecimal to100M(BigDecimal input) {
    return input.divide(BigDecimal.valueOf(1e8), 2, BigDecimal.ROUND_HALF_UP);
  }

  public static BigDecimal to10K(Long input) {
    return to10K(BigDecimal.valueOf(input));
  }


  /**
   * 计算法百分比
   */
  public static String toPercent(Long part, BigDecimal total, int scale) {
    return toPercent(BigDecimal.valueOf(part),total,scale);
  }
  public static String toPercent(BigDecimal part, BigDecimal total, int scale) {
    BigDecimal divide = part.divide(total,4, BigDecimal.ROUND_HALF_DOWN);
    // 下面将结果转化成百分比
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMaximumFractionDigits(scale);
    String perent = percentFormat.format(divide.doubleValue());
    return perent;
  }

  public static String toPercent(Long part, Long total, int scale) {
    return toPercent(BigDecimal.valueOf(part),BigDecimal.valueOf(total),scale);
  }
}

