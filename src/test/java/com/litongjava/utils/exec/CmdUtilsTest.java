package com.litongjava.utils.exec;

import org.junit.Test;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class CmdUtilsTest {

  @Test
  public void test() {
    String command = "ping 192.168.0.10";
    String result = CommonsExecUtils.exec(command);
    System.out.println(result);
  }
}
