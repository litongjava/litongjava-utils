package com.litong.utils.exec;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * @author bill robot
 * @date 2020年8月17日_下午1:59:09 
 * @version 1.0 
 * @desc
 */

public class ExecUtil {
  /**
   * 带返回结果的命令执行
   * @return
   */
  public static String execCmd(String command) {
    try {
      // 接收正常结果流
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      // 接收异常结果流
      ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
      // 解析命令
      CommandLine commandline = CommandLine.parse(command);
      // 获取命令执行器
      DefaultExecutor exec = new DefaultExecutor();
      exec.setExitValues(null);
      // 设置一分钟超时
      ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
      exec.setWatchdog(watchdog);
      //数据泵流处理器
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
      exec.setStreamHandler(streamHandler);
      //执行命令
      exec.execute(commandline);
      // 不同操作系统注意编码，否则结果乱码
      String out = outputStream.toString("GBK");
      String error = errorStream.toString("GBK");
      return out + error;
    } catch (Exception e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }

  public static void main(String[] args) {
    String command = "ping 192.168.0.10";
    String result = ExecUtil.execCmd(command);
    System.out.println(result);
  }
}
