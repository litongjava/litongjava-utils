package com.litongjava.utils.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by litonglinux@qq.com on 10/11/2023_3:22 PM
 * 内部维护一个Map,将配置写入文件
 */
public class ProjectConfig {
  private Map<String, Object> configs = new ConcurrentHashMap<>();
  private String configFileName = "app.properties";

  public ProjectConfig() {
    this.configs = readConfig();
  }

  public ProjectConfig(String configFileName) {
    this.configFileName = configFileName;
    this.configs = readConfig();
  }


  public String getConfigFileName() {
    return configFileName;
  }

  public Boolean getBool(String key, Boolean defaultValue) {
    Boolean value = (Boolean) configs.get(key);
    if (value == null) {
      return defaultValue;
    } else {
      return value;
    }
  }

  public Integer getInt(String key, Integer defaultValue) {
    Integer value = (Integer) configs.get(key);
    if (value == null) {
      return defaultValue;
    } else {
      return value;
    }
  }

  public String getStr(String key, String defaultValue) {
    String value = (String) configs.get(key);
    if (value == null) {
      return defaultValue;
    } else {
      return value;
    }
  }

  public void put(String key, Object value) {
    configs.put(key, value);
    saveConfig();
  }


  public void batchPut(Map<String, Object> map) {
    configs.putAll(map);
    saveConfig();
  }

  // 将configs保持到文件文件
  private void saveConfig() {
    Properties properties = new Properties();

    // Convert configs to properties
    for (Map.Entry<String, Object> entry : configs.entrySet()) {
      properties.setProperty(entry.getKey(), String.valueOf(entry.getValue()));
    }

    try (FileOutputStream out = new FileOutputStream(configFileName)) {
      properties.store(out, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 如果不存在则创建配置文件
   *
   * @return
   */
  private Map<String, Object> readConfig() {
    Properties properties = new Properties();
    Map<String, Object> resultMap = new HashMap<>();

    try (FileInputStream in = new FileInputStream(configFileName)) {
      properties.load(in);
      for (String key : properties.stringPropertyNames()) {
        resultMap.put(key, properties.getProperty(key));
      }
    } catch (FileNotFoundException e) {
      try (FileOutputStream out = new FileOutputStream(configFileName)) {
        properties.store(out, null);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return resultMap;
  }
}
