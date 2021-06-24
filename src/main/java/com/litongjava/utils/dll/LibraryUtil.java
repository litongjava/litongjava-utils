package com.litongjava.utils.dll;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryUtil {

  public static void addLibary() {
    File file = new File("lib");
    String absolutePath = file.getAbsolutePath();
    log.info("add lib:{}",absolutePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    try {
      addLibDir(absolutePath);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public static void addLibDir(String s) throws IOException {
    try {
      Field field = java.lang.ClassLoader.class.getDeclaredField("usr_paths");
      field.setAccessible(true);
      String[] paths = (String[]) field.get(null);
      for (int i = 0; i < paths.length; i++) {
        if (s.equals(paths[i])) {
          return;
        }
      }
      String[] tmp = new String[paths.length + 1];
      System.arraycopy(paths, 0, tmp, 0, paths.length);
      tmp[paths.length] = s;
      field.set(null, tmp);
    } catch (IllegalAccessException e) {
      throw new IOException("Failed to get permissions to set library path");
    } catch (NoSuchFieldException e) {
      throw new IOException("Failed to get field handle to set library path");
    }
  }
}