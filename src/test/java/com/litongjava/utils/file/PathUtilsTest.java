package com.litongjava.utils.file;

import org.junit.Test;

/**
 * @author create by ping-e-lee on 2021年7月10日 下午9:42:45 
 * @version 1.0 
 * @desc
 */
public class PathUtilsTest {

  @Test
  public void testGetParentPath1() {
    String path="/root/dev_workspace/java/1.java";
    String parentPath = PathUtils.getParentPath(path, "/");
    System.out.println(parentPath);
  }
  @Test
  public void testGetParentPath2() {
    String path="/root/dev_workspace/java";
    String parentPath = PathUtils.getParentPath(path, "/");
    System.out.println(parentPath);
  }

}
