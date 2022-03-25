package com.litong.utils.file;

import java.util.List;

import com.litongjava.utils.file.JavaFileUtil;

/**
 * @author litong
 * @date 2018年9月26日_下午2:48:45 
 * @version 1.0 
 */
public class JavaFileUtilTest {

  /**
   * 测试 查找指定 目录下的所有jar包
   */
  //@Test
  public void test() {
    String path="D:\\dev_workspace\\java\\litong_prject\\maven-util\\src\\main\\resources";
    List<String> jars = JavaFileUtil.findJar(path);
    for (String s : jars) {
      System.out.println(s);
    }
  }

}
