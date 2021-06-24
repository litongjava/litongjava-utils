package com.litongjava.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * @author litong 读取config.properties
 */
public class ConfigUtil {
  private static String configFilePath = null;
  private static Properties prop = null;
  private static InputStream ins = null;

  static {
    // 读取 mode.properties开始
    Properties modeProperties = new Properties();
    InputStream resourceAsStream = ConfigUtil.class.getResourceAsStream("/mode.properties");
    try {
      modeProperties.load(resourceAsStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String mode = modeProperties.getProperty("mode");
    configFilePath = "config-" + mode + ".properties";
    ins = ConfigUtil.class.getClassLoader().getResourceAsStream(configFilePath);
    // 防止读取乱码
    InputStreamReader insReader = null;
    try {
      insReader = new InputStreamReader(ins, "UTF-8");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    prop = new Properties();
    try {
      prop.load(insReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * get value of key
   */
  public static String getValue(String key) {
    return prop.getProperty(key);
  }

  /**
   * 设置值,设置值后存盘
   * @param key
   * @param value
   */
  public static void put(String key, String value) {
    prop.put(key, value);
    ConfigUtil.save();
  }

  public static void save() {
    URL url = ConfigUtil.class.getClassLoader().getResource(configFilePath);
    File file = new File(url.getFile());
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      prop.store(fileOutputStream, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * get properties
   */
  public static Properties getProperties() {
    return prop;
  }

  /**
   * 
   * @return 配置文件名称
   */
  public static String getConfigFile() {
    return configFilePath;
  }

  public static void main(String[] args) {
    // String value = ConfigUtil.getValue("prorject_id");
    // System.out.println(value);

    Properties prop = ConfigUtil.getProperties();
    Set<Entry<Object, Object>> entrySet = prop.entrySet();
    for (Entry<Object, Object> entry : entrySet) {
      System.out.println("" + entry.getKey() + "=" + entry.getValue());
    }
  }
}