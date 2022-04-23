package com.litongjava.utils.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2019年1月11日_下午4:29:05 
 * @version 1.0 
 */
@Slf4j
public class JSONFileUtil {
  private static String configPath = null;
  static {
    // 如果config文件夹不存在,则创建config文件夹
    URL url = JSONFileUtil.class.getClassLoader().getResource("");
    configPath = url.getFile() + File.separator + "config";
    File file = new File(configPath);
    if (!file.exists())
      file.mkdirs();
  }

  /**
   * 保存json文件到
   * @param t
   * @param filename
   */
  public static <T> void save(T t, String filename) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter(new File(configPath + File.separator + filename));
      JSON.writeJSONString(fileWriter, t);
    } catch (IOException e1) {
      log.info("写入配置文件错误");
      e1.printStackTrace();
    } finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}