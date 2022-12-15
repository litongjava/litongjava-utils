package com.litongjava.utils.thread;

import org.junit.Test;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class ThreadPoolUtilsTest {
  
  @Test
  public void test01() {
    int availableProcessors = Runtime.getRuntime().availableProcessors();
    System.out.println(availableProcessors);
  }

}
