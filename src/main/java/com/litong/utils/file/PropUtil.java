package com.litong.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author bill robot
 * @date 2020年4月14日_下午7:38:24 
 * @version 1.0 
 * @desc 读取properties 文件
 */
public class PropUtil {
  private static Properties prop = null;
  private static InputStream ins = null;

  static {
    // 读取 properties开始
    ins = PropUtil.class.getResourceAsStream("/application.properties");

    // InputStream转为InputStreamReader
    InputStreamReader insReader = null;
    try {
      // 防止读取乱码
      insReader = new InputStreamReader(ins, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    // InputStreamReader转为Properties
    try {
      prop = new Properties();
      prop.load(insReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * get value of key
   */
  public static String get(String key) {
    return prop.getProperty(key);
  }

  /**
   * 设置值,设置值后存盘
   * @param key
   * @param value
   */
  public static void put(String key, String value) {
    prop.put(key, value);
  }

  /**
   * get properties
   */
  public static Properties getProperties() {
    return prop;
  }
}
