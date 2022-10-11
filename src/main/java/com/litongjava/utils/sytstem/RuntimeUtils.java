package com.litongjava.utils.sytstem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.litongjava.utils.io.IOUtils;

/**
 * @author litong
 * @date 2018年5月3日_上午11:33:56
 * @version 1.0 runtime的工具类
 */
public class RuntimeUtils {

  private static String osType;

  /**
   * 判断操作系统的类型,确定使用命令前缀 windows ==> cmd /c linux ==> sh -c
   */
  static {
    osType = System.getProperty("os.name");
  }

  /**
   * 接受命令,根据命令判断操作系统的类型,使用cmd或者说sh
   * 
   * @return 返回命令执行结果
   * @throws IOException
   */
  public static String exec(String cmd) {
    StringBuffer result = new StringBuffer();
    StrBufUtils.appendln(result, "command line:");
    StrBufUtils.appendln(result, cmd);
    
    cmd = "\"" + cmd + "\"";
    Process process = null;
    if (osType.startsWith(OSType.LINUX)) {
      try {
        process = shc(cmd);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (osType.startsWith(OSType.WINDOWS)) {
      try {
        process = cmdc(cmd);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        throw new IOException("unsuppoort os type");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    if(process==null) {
      StrBufUtils.appendln(result, "erros process is null");
      return result.toString();
    }
    String retVal = getProcessResult(process,result);
    return retVal;
  }

  /**
   * 获取Process的执行结果
   * @param process
   * @return
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  public static String getProcessResult(Process process,StringBuffer strBuf) {
    String osType = OsTypeUtils.getOsType();
    BufferedReader stdin =null;
    BufferedReader stderr =null;
    try{
      InputStream inputStream = process.getInputStream();
      
      if(osType.startsWith(OSType.WINDOWS)) {
        stdin = new BufferedReader(new InputStreamReader(inputStream,"GBK"));
      }else {
        stdin = new BufferedReader(new InputStreamReader(inputStream));
      }
   // BufferedReader中的字符串放到strBuf中
      StrBufUtils.appendln(strBuf, "std:");
      String line = null;
      while ((line = stdin.readLine()) != null) {
        StrBufUtils.appendln(strBuf, line);
      }

      InputStream errorStream = process.getErrorStream();
      // 流已经关闭了,还可以再使用吗??
      // 可以使用
      if(osType.startsWith(OSType.WINDOWS)) {
        stderr = new BufferedReader(new InputStreamReader(errorStream,"GBK"));
      }else {
        stderr = new BufferedReader(new InputStreamReader(errorStream));
      }
      
      StrBufUtils.appendln(strBuf, "err:");
      while ((line = stderr.readLine()) != null) {
        StrBufUtils.appendln(strBuf, line);
      }
    }catch(IOException e) {
      e.printStackTrace();
    }finally {
      IOUtils.closeQuietly(stderr);
      IOUtils.closeQuietly(stdin);
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
