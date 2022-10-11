package com.litongjava.utils.exec;

import java.io.ByteArrayOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import com.litongjava.utils.sytstem.OSType;
import com.litongjava.utils.sytstem.OsTypeUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ping E Lee
 * @date 2020年8月17日_下午1:59:09
 * @version 1.0
 * @desc
 */

@Slf4j
public class CommonsExecUtils {
  /**
   * 带返回结果的命令执行
   * 
   * @return
   */
  public static String exec(String command) {
    // 接收正常结果流
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // 接收异常结果流
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    try {
      // 解析命令
      CommandLine commandline = CommandLine.parse(command);
      // 获取命令执行器
      DefaultExecutor exec = new DefaultExecutor();
      exec.setExitValues(null);
      // 设置一分钟超时
      ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
      exec.setWatchdog(watchdog);
      // 数据泵流处理器
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
      exec.setStreamHandler(streamHandler);
      // 执行命令
      exec.execute(commandline);
      // 不同操作系统注意编码，否则结果乱码
      // String out = outputStream.toString("GBK");
      // String error = errorStream.toString("GBK");
      String osType = OsTypeUtils.getOsType();
      log.info(osType);
      String out = null;
      String error = null;
      if (osType.contains(OSType.WINDOWS10) || osType.contains(OSType.WINDOWS)) {
        out = outputStream.toString("GBK");
        error = errorStream.toString("GBK");
      } else {
        out = outputStream.toString();
        error = errorStream.toString();
      }
      return out + error;
    } catch (Exception e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }

}
