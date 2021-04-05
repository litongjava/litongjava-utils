package com.litong.utils.des;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

/**
 * @author create by ping-e-lee on 2021年3月16日 下午4:28:01 
 * @version 1.0 
 * @desc
 */
public class DesUtilsTest {

  String keyString = "66669999";
  
  @Test
  public void encrypt() throws Exception {
    
    String plainText = "0234061adsa=d&张三?#";
    String encryptedText = DesUtils.encrypt(plainText, keyString);
    System.out.println(encryptedText); //cf9d03de8901caf0a9e4c9e1897df563531edd8b4a2ad917
  }
  
  @Test
  public void descrypt() throws Exception {
    String cipherText="cf9d03de8901caf0a9e4c9e1897df563531edd8b4a2ad917";
    String plainText = DesUtils.decrypt(cipherText, keyString);
    System.out.println(plainText);
  }
  
  @Test
  public void toHex() throws DecoderException {
    System.out.println(Hex.encodeHex("litong".getBytes()));
    System.out.println(Hex.encodeHex("李通".getBytes()));
  }
}
