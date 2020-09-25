package com.litong.utils.file;

import java.io.File;

/**
 * @author bill robot
 * @date 2020年9月6日_下午8:46:48 
 * @version 1.0 
 * @desc 路径工具类
 */
public class PathUtils {

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
