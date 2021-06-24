package com.litongjava.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author litong
 * @date 2018年9月26日_下午1:35:57 
 * @version 1.0
 * java 文件工具类 
 */

public class JavaFileUtil {

  // jars 存放jar包的去路径
  private static List<String> jars = new ArrayList<>();
  
  
  public static List<String> findJar(String absPath) {
    jars.clear();
    //查找jar包,添加到 jars 变量中
    onlyFindJar(absPath);
    return jars;
  }

  /**
   * 查找指定路径下的所有jar包
   * @param absPath jar包所在目录的绝对路径
   * @return
   */
  public static void onlyFindJar(String absPath) {
    // retval 保存返回值
    File jarFolderFile = new File(absPath);
    File[] listFiles = jarFolderFile.listFiles();
    for (File f : listFiles) {
      String absolutePath = f.getAbsolutePath();
      boolean isDirectory = f.isDirectory();
      if (isDirectory) {
        // 如果是目录,继续递归
        onlyFindJar(absolutePath);
      } else {
        // 如果不是目录,判断是否是jar包
        boolean isJar = absolutePath.endsWith(".jar");
        if (isJar) {
          // 如果是jar包,添加到list
          jars.add(absolutePath);
        }
      }
    }
  }
}