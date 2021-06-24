package com.litongjava.utils.digest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.IOUtils;

/**
 * @author litong
 * @date 2019年2月13日_下午4:08:29
 * @version 1.0
 */
public class SHA1Util {

  private static MessageDigest instance = null;

  static {
    try {
      instance = MessageDigest.getInstance("SHA-1"); // 获取SHA-1消息签名
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取文件的20位16进制sha1值
   */
  public static String getSha1(String localFilePath) {
    File file = new File(localFilePath);
    if (!file.exists()) {
      // log.info("file not found:"+localFilePath);
      System.out.println("file not found:" + localFilePath);
      return null;
    } else {
      String sha1 = getSha1(file);
      return sha1;
    }
  }

  /**
   * 获取文件20位16进制sha1值
   *
   * @param localFilePath
   * @return
   */
  public static String getSha1(File localFilePath) {

    BufferedInputStream bis = null;
    byte[] buffers = new byte[1024 * 1024 * 2];
    int len = 0;
    try {
      bis = new BufferedInputStream(new FileInputStream(localFilePath));
      while ((len = bis.read(buffers)) > 0) {
        instance.update(buffers, 0, len); // 添加需要签名的的数据
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(bis);
    }

    byte[] digest = instance.digest(); // 对消息镜像签名
    BigInteger bigInteger = new BigInteger(1, digest);
    String string = bigInteger.toString(16);// 转换成16进制
    int length = 40 - string.length();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      sb.append("0");
    }
    sb.append(string);
    return sb.toString();
  }

  /**
   * 加密
   */
  public static String encode(String content) {
    instance.update(content.getBytes());
    byte[] digest = instance.digest();
    BigInteger bigInteger = new BigInteger(1, digest);
    String string = bigInteger.toString(16);
    return string;
  }
}
