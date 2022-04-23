package com.litongjava.utils.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.litongjava.utils.integer.HexUtils;

/**
 * AES CRT加密模式工具类
 */
public class AESCrtUtils {

  public static byte[] getDefaultIvBytes() {
    byte[] ivBytes = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
    return ivBytes;
  }

  public static String getRandomKey() {
    byte[] bytes = new byte[16];
    Random random = new Random();
    random.nextBytes(bytes);
    return HexUtils.toHexString(bytes);
  }

  /*
   * AES CRT加密
   * @param content 要加密的内容
   * @param keyBytes 加密文件的秘钥
   * @param ivBytes 加密的偏移量
   * @return 输出Hex十六进制再次加密的结果
   **/
  public static String encrypt(String content, byte[] keyBytes, byte[] ivBytes) {
    String encodeStr = null;
    try {
      // 产生密钥 构建SecretKeySpec，初始化Cipher对象时需要该参数
      SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
      // 构建Cipher对象，需要传入一个字符串，格式必须为"algorithm/mode/padding"或者"algorithm/",意为"算法/加密模式/填充方式"
      Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
      // 初始化Cipher对象
      IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
      // 加密数据
      byte[] resultBytes = cipher.doFinal(content.getBytes());
      encodeStr = HexUtils.toHexString(resultBytes);
      return encodeStr;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
    // 结果用Hex十六进制转码
    return encodeStr;
  }

  /**
   * AES CTR 解密
   * @param encodeBytes 加密过后的文件
   * @param keyBytes     偏移量
   * @param ivBytes    秘钥文件
   */
  public static byte[] decrypt(byte[] encodeBytes, byte[] keyBytes, byte[] ivBytes) {
    try {
      IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
      Key keys = new SecretKeySpec(keyBytes, "AES");
      Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, keys, ivSpec);
      byte[] ret = cipher.doFinal(encodeBytes);
      return ret;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密
   * @param encode
   * @param key
   * @return
   */
  public static String decrypt(String encode, String key) {
    byte[] encodeBytes = HexUtils.toBytes(encode);
    byte[] keyBytes = HexUtils.toBytes(key);
    byte[] decrypt = decrypt(encodeBytes, keyBytes, getDefaultIvBytes());
    return new String(decrypt);
  }
}