package com.litongjava.utils.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author litong
 * @date 2018年11月5日_上午9:11:44 
 * @version 1.0
 * InputStream工具类 
 */
public class InputStreamUtil {
  /**
   * 将InputStream中的流转成字符串
   * @param in
   * @return
   * 使用Scanner扫描流,获取流中的字符
   */
  public static String ConvertToString(InputStream in) {
    Scanner scanner = new Scanner(in);
    String retval = null;
    retval = scanner.hasNext() ? scanner.next() : "";
    scanner.close();
    return retval;
  }

  /**
   * 将InputStream中的流转成字符串
   * @param in
   * @param encode
   * @return
   */
  public static String ConvertToString(InputStream in, String encode) {
    StringBuffer stringBuffer = new StringBuffer();
    String str = null;
    BufferedReader bufferedReader = null;
    try {
      // 使用转换流,将字节流转成字符流,经字符流转换成缓冲流
      bufferedReader = new BufferedReader(new InputStreamReader(in, encode));
      while ((str = bufferedReader.readLine()) != null) {
        stringBuffer.append(str);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return stringBuffer.toString();
  }
}
