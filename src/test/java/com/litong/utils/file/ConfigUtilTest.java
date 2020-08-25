package com.litong.utils.file;

import java.io.File;
import java.net.URL;

import org.junit.Test;

/**
 * @author Administrator
 * @date 2019年1月10日_下午12:02:00 
 * @version 1.0 
 */
public class ConfigUtilTest {

  @Test
  public void test() {
    URL url = this.getClass().getClassLoader().getResource("config.properties");
    String filePath = url.getFile();
    File file = new File(filePath);
    System.out.println(file.exists());
  }
  /**
   * 添加值,存盘
   */
  @Test
  public void store() {
    ConfigUtil.put("提示问数量","20");
    String value = ConfigUtil.getValue("知识库地址");
    System.out.println(value);
  }
}
