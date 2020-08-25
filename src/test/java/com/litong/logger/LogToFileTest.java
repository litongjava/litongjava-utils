package com.litong.logger;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

/**
 * @author litong
 * @date 2018年9月21日_上午10:23:55 
 * @version 1.0 
 */
public class LogToFileTest {

  @Test
  public void test() {
    fail("Not yet implemented");
  }

  /**
   * 测试 java.io.File的构造中能不能接受文件夹/文件名
   * 支持
   * @return 
   */
  @Test
  public void test1() {
    File file = new File("file");
    file.mkdirs();
    File file2 = new File("file" + File.separator + "my.log");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(file2.getAbsolutePath());
  }

  /**
   * 获取classpath路径
   */
  @Test
  public void getClassPath() {
    URL resource = LogToFileTest.class.getClassLoader().getResource("//");
    System.out.println(resource.toString());
  }
}
