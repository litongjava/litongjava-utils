package com.litongjava.utils.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by litong on 2018/7/25 0025.
 */
public class SerializableUtil {
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
      out = new ObjectOutputStream(new FileOutputStream(path));
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
      in = new ObjectInputStream(new FileInputStream(path));
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
