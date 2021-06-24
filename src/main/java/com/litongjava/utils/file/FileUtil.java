package com.litongjava.utils.file;

import java.io.File;

/**
 * Created by litong on 2018/4/12 0012.
 * 处理文件的工具类
 */
public class FileUtil {

  private static String startWith = "file:/";
  private static int startLen = "file:/".length();

  /**
   * 传入文件的绝对路径+拓展名，返回文件名+拓展名
   *
   * @param pathName windows
   *                 eg. 1 在文件系统中的绝对路径
   *                 D:\dev_ideaProjects\smart-ivr\smart-ivr-payment\src\main\resources\public\scene1\force-硅基客户-360.wav
   *                 eg.2 在项目中的相对路径
   *                 src\main\resources\public\scene1\force-硅基客户-360.wav
   *                 <p>
   *                 linux
   *                 eg. 1 在文件系统中的绝对路径
   *                 /usr/root/wrokspace/smart-ivr/smart-ivr-payment/src/main/resources/public/scene1/force-硅基客户-360.wav
   *                 eg.2 在项目中的相对路径
   *                 src/main/resources/public/scene1/force-硅基客户-360.wav
   * @return
   */
  public static String getFileName(String pathName) {
    // 10分无奈，只能使用File的处理方法,
    // return new File(absFileName).getName();

    // 得到文件的分割符
    int index = pathName.lastIndexOf(File.separatorChar);
    // prefixLength类是从哪里来的SelectorContext，竟然可以在这是直接使用
    if (index < 5)
      return pathName.substring(5);
    return pathName.substring(index + 1);
    /*
     * 如果文件名是 /1/1.txt index=5 ==>详细原因我也不知道，所以if语句几乎不会执行
     */
  }

  /**
   * 获取文件绝对路径
   *
   * @return
   */
  public static String getAbsolutePath(File file) {
    String absolutePath = file.getAbsolutePath();
    absolutePath = getAbsolutePath(absolutePath);
    return absolutePath;
  }

  /**
   * 获取文件全路径
   */
  public static String getAbsolutePath(String absolutePath) {
    if (absolutePath.startsWith(startWith)) {
      // 如果是win平台,是 file:/开头,删除file:/
      String substring = absolutePath.substring(startLen, absolutePath.length());
      return substring;
    } else {
      return absolutePath;
    }
  }
}
