package com.litongjava.utils.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessUtils {

  public static Process start(List<String> commands) throws IOException {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(commands);
    return builder.start();
  }

  public static String start(String command) {
    Process process = null;
    System.out.println("start run cmd:" + command);
    try {
      process = Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      e.printStackTrace();
      log.info("run cmd exception:{}", command);
      return null;
    }
    String retval = printRunInfo(process);
    process.destroy();
    return retval;

  }

  /**
   * 获取Error信息和Output信息
   * 
   * @param process
   */
  private static String printRunInfo(Process process) {
    StringBuffer retval = new StringBuffer();
    // 如果合并大视频文件会产生大量的日志缓存导致线程阻塞，最终合并失败，所以加两个线程处理日志的缓存，之后再调用waitFor方法，等待执行结果。
    new Thread() {
      @Override
      public void run() {

        String line = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
          while ((line = in.readLine()) != null) {
            retval.append("output:" + line+"\r\n");
            System.out.println("output:" + line);
          }
        } catch (IOException e) {
          log.error("读取流现异常:{}",e.getMessage());
        }
      }
    }.start();

    new Thread() {
      @Override
      public void run() {

        String line = null;
        try (BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));) {
          while ((line = err.readLine()) != null) {
            retval.append("err:" + line+"\r\n");
            System.err.println("err:" + line);
          }
          
        } catch (IOException e) {
          log.error("读取流现异常:{}",e.getMessage());
        }
      }
    }.start();

    // 等待命令子线程执行完成
    try {
      process.waitFor();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    return retval.toString();
  }
}
