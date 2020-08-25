package com.litong.utils.file;

import java.io.File;

import org.junit.Test;

/**
 * @author litong
 * @date 2018年9月26日_下午4:15:15 
 * @version 1.0 
 */
public class FileUtilTest {

  @Test
  public void test() {
    String path = "d:\\dev\\java\\path";
    File file = new File(path);
    String absolutePath = FileUtil.getAbsolutePath(file);
    System.out.println(absolutePath);
  }
}
