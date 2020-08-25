package com.litong.utils.date;

import java.util.Date;

import org.junit.Test;

/**
 * @author litong
 * @date 2018年9月25日_上午7:54:33 
 * @version 1.0 
 */
public class DateUtilTest {

  @Test
  public void test() {
    Date d1 = new Date();
    System.out.println(d1);
    // 获取 1970-1-1 0:0:0 到现在经过的毫秒数
    long milliSecond = d1.getTime();
    System.out.println(milliSecond);
    // 将毫秒数转换成整天数
    long day = milliSecond / (24 * 60 * 60 * 1000);
    long temp1 = day * (24 * 60 * 60 * 1000);
    Date date = new Date(temp1);
    // 问题1:会多出来8个小时
    System.out.println(date);
  }

  /**
   * 测试 getTime方法
   * 获取的是 当前时间到gmt+8经过的毫数
   */
  @Test
  public void test1() {
    Date d1 = new Date();
    System.out.println(d1);
    long milliSecond = d1.getTime();
    System.out.println(milliSecond);
    Date d2 = new Date(milliSecond);
    System.out.println(d2);
  }

  @Test
  public void getFirstDayOfYear() {
    Date date = new Date();
    Date firstDayOfYear = DateUtil.getFirstDayOfYear(date);
    System.out.println(firstDayOfYear);
  }
  /**
   * 测试 getTime方法
   */
  // public void test2() {
  //
  // }
  //
  /**
   * 获取时区
   */
  // @Test
  // public void test2() {
  //
  // }

}
