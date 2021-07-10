package com.litongjava.utils.file;

import java.io.File;

/**
 * @author litongjava
 * @date 2020年9月6日_下午8:46:48 
 * @version 1.0 
 * @desc 路径工具类
 */
public class PathUtils {

  /**
   * 获取路径的父路径 * 
   * <pre>
   * /root/dev_workspace/java/1.java==>/root/dev_workspace/java
   * /root/dev_workspace/java ==>/root/dev_workspace
   * </pre>
   * 
   * @return
   */
  public static String getParentPath(String path, String separator) {
    // 拆分,结果如下[, root, dev_workspace, java, 1.java],第一个元素也会拆分
    String[] array = path.split(separator);
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 1; i < array.length - 1; i++) {
      stringBuffer.append("/").append(array[i]);
    }
    return stringBuffer.toString();
  }

  /**
   * 拼接成1个路径,最后也添加
   * @return
   */
  public static StringBuffer joint(String... array) {
    StringBuffer stringBuffer = new StringBuffer();
    int len = array.length;
    for (int i = 0; i < len; i++) {
      stringBuffer.append(array[i] + File.separator);
    }
    return stringBuffer;
  }

}
