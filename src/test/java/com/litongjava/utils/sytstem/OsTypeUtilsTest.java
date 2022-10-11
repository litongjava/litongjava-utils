package com.litongjava.utils.sytstem;

import org.junit.Test;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class OsTypeUtilsTest {

  @Test
  public void test() {
    String osType = OsTypeUtils.getOsType();
    System.out.println(osType);
  }

}
