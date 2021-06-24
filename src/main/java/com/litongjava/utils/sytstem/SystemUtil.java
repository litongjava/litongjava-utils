package com.litongjava.utils.sytstem;

/**
 * @author litong
 * @date 2018年9月21日_上午9:26:33 
 * @version 1.0
 * 系统工具类,封装 System.getProperty属性 
 */
public class SystemUtil {

  private static String fileSeprator;
  private static String userDir;

  static {
    fileSeprator = System.getProperty("file.separator");
    userDir = System.getProperty("user.dir");
  }

  /**
   * 获取操作系统的文件分隔符
   * @return
   */
  public static String getFileSeparator() {
    return fileSeprator;
  }

  /**
   * 获取程序运行目录
   * @param args
   */
  public static String getUserDir() {
    return userDir;
  }

  public static void main(String[] args) {
    String dir = SystemUtil.getUserDir();
    System.out.println(dir);
  }
}
