package com.litongjava.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2019年1月11日_下午4:29:05 
 * @version 1.0 
 */
public class JsonFileUtils {
  private static String configPath = null;
  static {
    // 如果config文件夹不存在,则创建config文件夹
    URL url = JsonFileUtils.class.getClassLoader().getResource("");
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
    File file = new File(configPath + File.separator + filename);

    try (FileOutputStream out = new FileOutputStream(file)) {
      JSON.writeTo(out, t, JSONWriter.Feature.PrettyFormat);
    } catch (FileNotFoundException e2) {
      e2.printStackTrace();
    } catch (IOException e2) {
      e2.printStackTrace();
    }
  }
}