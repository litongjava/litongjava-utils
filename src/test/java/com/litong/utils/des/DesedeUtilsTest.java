package com.litong.utils.des;

import org.junit.Test;

/**
 * @author create by ping-e-lee on 2021年3月16日 下午4:13:32 
 * @version 1.0 
 * @desc
 */
public class DesedeUtilsTest {

  private String keyString = "abcaaaaaaaaaaaaaaaaxgxga";

  @Test
  public void encode() throws Exception {
    String cipherText = DesedeUtils.encode("李通", keyString);
    System.out.println(cipherText); // ==5a0e95c465acd754
  }

  @Test
  public void decode() throws Exception {
    String plainText = DesedeUtils.decode("5a0e95c465acd754", keyString);
    System.out.println(plainText);
  }
}
