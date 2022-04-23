package com.litongjava.utils.temp;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

/**
 * @author create by Ping E Lee on 2022年4月15日 下午8:41:32 
 * 创建临时文件夹工具类
 */
@Slf4j
public class TempFolderUtils {
  public static File getTempFolder() {
    File tempFolder = new File(ConfigConstants.TEMP_DIR_NAME);
    if (!tempFolder.exists()) {
      boolean isSuccess = tempFolder.mkdirs();
      if (isSuccess) {
        log.info("mkdir success:{} ", tempFolder.getAbsolutePath());
      } else {
        log.info("mkdir fail:{} ", tempFolder.getAbsolutePath());
        return null;
      }
    }
    return tempFolder;
  }
}
