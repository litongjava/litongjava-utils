package com.litongjava.utils.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * desede是对des加密的改进版
 * @author litong
 * @date 2019年3月1日_上午10:09:05 
 * @version 1.0 
 */
public class DesedeUtils {
  public static final String CHARSET = "utf-8";
  public static String transformation = "DESede/ECB/PKCS5Padding";
  public static String algorithm = "DESede";

  /**
   * 加密
   * @param src
   * @param ssoKey
   * @return
   * @throws Exception
   */
  public static String encode(String src, String ssoKey) throws Exception {
    SecretKey secretKey = new SecretKeySpec(ssoKey.getBytes(CHARSET), algorithm); // 秘钥+算法=secretKey
    Cipher cipher = Cipher.getInstance(transformation); // 转换器=>密码实例
    cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 加密模式,secretKey==>初始化
    byte[] b = cipher.doFinal(src.getBytes(CHARSET)); // 加密
    return new String(Hex.encodeHex(b));
  }

  /**
   * 解密
   * @param dest
   * @param ssoKey
   * @return
   * @throws Exception
   */
  public static String decode(String dest, String ssoKey) throws Exception {
    SecretKey secretKey = new SecretKeySpec(ssoKey.getBytes(CHARSET), algorithm);
    Cipher cipher = Cipher.getInstance(transformation);
    cipher.init(Cipher.DECRYPT_MODE, secretKey); // 解密模式,secretKey==>初始化
    byte[] b = cipher.doFinal(Hex.decodeHex(dest.toCharArray())); // 解码
    return new String(b, CHARSET);
  }

}