package com.litongjava.utils.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author litong
 * @date 2019年1月11日 下午3:32:37
 */
@Slf4j
public class SerializableUtils {
  private static String configPath = null;

  static {
    // 如果configPath下存在 config文件夹,则创建config文件夹
    URL url = SerializableUtils.class.getClassLoader().getResource("");
    configPath = url.getFile() + File.separator + "config";
    File file = new File(configPath);
    if (!file.exists()) {
      // 创建config文件夹
      log.info("创建config文件夹:" + configPath);
      file.mkdirs();
    }
  }

  /**
   * write to file
   *
   * @param obj
   * @param path
   * @return file path
   */
  public static String writeToFile(Object obj, String path) {
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(new FileOutputStream(configPath + File.separator + path));
      out.writeObject(obj);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return path;
  }

  /**
   * read from file
   *
   * @param clazz
   * @param path
   * @param <T>
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @SuppressWarnings("unchecked")
  public static <T> T readFromFile(Class<T> clazz, String path) {
    T t = null;
    ObjectInputStream in = null;
    try {
      in = new ObjectInputStream(new FileInputStream(configPath + File.separator + path));
      t = (T) in.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }
    return t;
  }
}