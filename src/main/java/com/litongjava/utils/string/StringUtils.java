package com.litongjava.utils.string;

import java.io.File;import org.terracotta.offheapstore.storage.listener.ListenableStorageEngine;

/**
 * @author litong
 * @date 2018年9月26日_下午6:40:39
 * @version 1.0
 */
public class StringUtils {
  public static boolean equals(String source, String dest) {
    if (source == null) {
      return false;
    } else {
      return source.equalsIgnoreCase(dest);
    }
  }

  public static boolean isEmpty(String value) {
    if (value == null || value.length() == 0) {
      return true;
    }
    return false;
  }
  
  public static boolean isBlank(String value) {
    if (value == null || value.length() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(Object value) {
    return value == null;
  }
  

  public static boolean notBlank(String value) {
    if (isEmpty(value)) {
      return false;
    }
    return true;
  }

  /**
   * 删除指定文string的后缀
   * @param string
   * @param suffix
   * @return
   */
  public static String removeSuffix(String string, String suffix) {
    boolean b = string.endsWith(suffix);
    if (!b) {
      // 如果不包含后缀,退出
      return null;
    }
    int lastIndexOf = string.lastIndexOf(suffix);
    String substring = string.substring(0, lastIndexOf);
    return substring;
  }

  /**
   * 删除指定string的前缀
   * @param string
   * @param prefix
   * @return
   */
  public static String removePrefix(String string, String prefix) {
    boolean b = string.startsWith(prefix);
    if (!b) {
      // 如果不包含前缀,退出
      return null;
    }
    int length = prefix.length();
    String substring = string.substring(length);
    return substring;
  }

  /**
   * 分隔路径,使用与linux和win
   * @param path
   * @return
   */
  public static String[] splitPath(String path) {
    path = path.replace(File.separator, "/");
    String[] split = path.split("/");
    return split;
  }

  public static boolean isNumeric(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * 如果为空,返回 0
   * @param employeeDepartmentId
   * @return
   */
  public static String nullTo0(String employeeDepartmentId) {
    if (StringUtils.isEmpty(employeeDepartmentId)) {
      return "0";
    }
    return null;
  }

  /**
   * 连接字符串
   * @param joinChar 连接符
   * @param strs 连接数组
   * @return
   */
  public static String join(String joinChar, String... strs) {
    StringBuffer stringBuffer = new StringBuffer();
    int length=strs.length;
    for(int i=0;i<length-1;i++) {
      stringBuffer.append(strs[i]+joinChar);
    }
    //拼接最后一个字符
    stringBuffer.append(strs[length-1]);
    return stringBuffer.toString();
    
  }
}