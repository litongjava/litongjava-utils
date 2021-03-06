package com.litongjava.utils.sytstem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author litong
 * @date 2018年5月3日_上午11:33:56
 * @version 1.0 runtime的工具类
 */
public class RuntimeUtils {

  public static final String LINUX = "Linux";
  public static final String WINDOWS = "Windows";

  private static String osType;

  /**
   * 判断操作系统的类型,确定使用命令前缀 windows ==> cmd /c linux ==> sh -c
   */
  static {
    osType = System.getProperty("os.name");
  }
  
  public static String getOsType() {
    return osType;
  }

  /**
   * 接受命令,根据命令判断操作系统的类型,使用cmd或者说sh
   * 
   * @return 返回命令执行结果
   * @throws IOException
   */
  public static String exec(String cmd) throws IOException {
    cmd = "\"" + cmd + "\"";
    Process process = null;
    if (osType.startsWith(LINUX)) {
      process = shc(cmd);
    } else if (osType.startsWith(WINDOWS)) {
      process = cmdc(cmd);
    } else {
      throw new IOException("unsuppoort os type");
    }
    InputStream inputStream = process.getInputStream();

    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
    StringBuffer strBuf = new StringBuffer();
    StrBufUtil.appendln(strBuf, "执行命令" + cmd);
    StrBufUtil.appendln(strBuf, "std:");

    // BufferedReader中的字符串放到strBuf中

    String line = null;
    while ((line = bufReader.readLine()) != null) {
      strBuf.append(line).append(System.getProperty("line.separator"));
    }

    InputStream errorStream = process.getErrorStream();
    // 流已经关闭了,还可以再使用吗??
    // 可以使用
    bufReader = new BufferedReader(new InputStreamReader(errorStream, "GBK"));

    // 根据不同的操作系统类型添加换行符
    if (osType.startsWith(WINDOWS)) {
      strBuf.append("\r\n");
    } else {
      strBuf.append("\n");
    }
    strBuf.append("err:");
    while ((line = bufReader.readLine()) != null) {
      strBuf.append(line);
    }
    String retVal = strBuf.toString();
    return retVal;
  }

  /**
   * 执行cmd命令
   */
  public static Process cmdc(String cmd) throws IOException {
    Process exec = Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmd });
    return exec;
  }

  /**
   * 执行sh命令
   * 
   * @throws IOException
   */
  public static Process shc(String cmd) throws IOException {
    Process exec = Runtime.getRuntime().exec(new String[] { "sh", "-c", cmd });
    return exec;
  }
}
