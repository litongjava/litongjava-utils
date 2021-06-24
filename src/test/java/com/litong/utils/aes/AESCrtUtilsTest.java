package com.litong.utils.aes;

import org.junit.Test;

import com.litongjava.utils.aes.AESCrtUtils;
import com.litongjava.utils.integer.HexUtils;

/**
 * @author bill robot
 * @date 2020年8月28日_下午7:26:31 
 * @version 1.0 
 * @desc
 */
public class AESCrtUtilsTest {

  private String plainText = "admin";
  private String key = "c6011fb91db818b473ad591436667ff2";
  //private String key = "5109e1eaeaff18ffba6db811d1eb7600";
  private String cipherText = "bcb7d4e425";

  @Test
  public void encrypt() {
    byte[] keyBytes = HexUtils.toBytes(key);
    String decrypt = AESCrtUtils.encrypt(plainText, keyBytes, AESCrtUtils.getDefaultIvBytes());
    System.out.println(decrypt);
  }

  @Test
  public void getRandomKey() {
    String randomKey = AESCrtUtils.getRandomKey();
    System.out.println(randomKey);
  }

}
