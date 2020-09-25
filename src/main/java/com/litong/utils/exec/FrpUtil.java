package com.litong.utils.exec;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年8月17日_下午2:23:08 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class FrpUtil implements Runnable {
  public static void start() {
    File frpcExe = new File("lib/frp/frpc.exe");
    File frpcIni = new File("lib/frp/frpc.ini");
    String command = frpcExe.getAbsolutePath() + " -c " + frpcIni.getAbsolutePath();
    log.info("启动frp:{}", command);
    String result = ExecUtil.execCmd(command);
    log.info("启动frp结果:{}", result);
  }

  @Override
  public void run() {
    FrpUtil.start();
  }
}
