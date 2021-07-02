package com.litongjava.utils.digest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 只使用md5加密是不行的，很容易被破解。常见的做法有：
 * 先对原串进行一些处理，比如先给它拼接一个字符串常量，再进行md5加密。即使对方解密，也获取不到原串。
 * 使用多种加密算法。比如先用md5加密，再对加密的结果使用其它加密算法进行加密。比如先使用md5加密，对加密的结果再次使用md5加密。
 * 这2种方式方式经常一起使用
 * @author litongjava
 * @date 2020年8月29日 下午5:06:34
 * @desc
 */
public class MD5Utils {

  /**
   * 加密
   * @param src
   * @return
   */
  public static String encode(String src) {
    byte[] srcBytes = src.getBytes();
    byte[] encode = encode(srcBytes);
    return byteToString(encode);
  }

  /**
   * 对字符串进行加密
   * @param src
   * @param charset
   * @return
   */
  public static String encode(String src, String charset) {
    byte[] srcBytes = null;
    try {
      srcBytes = src.getBytes(charset);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    byte[] digest = encode(srcBytes);
    String encode = byteToString(digest);
    return encode;
  }

  /**
   * 加密
   * @param srcBytes
   * @return
   */
  private static byte[] encode(byte[] srcBytes) {
    byte[] digest = null;
    try {
      MessageDigest md5 = MessageDigest.getInstance("md5");
      digest = md5.digest(srcBytes);
      return digest;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 转成字符串
   * @param srcBytes
   * @return
   */
  private static String byteToString(byte[] digest) {
    // 16是表示转换为16进制数
    String encode = new BigInteger(1, digest).toString(16);
    return encode;
  }

}