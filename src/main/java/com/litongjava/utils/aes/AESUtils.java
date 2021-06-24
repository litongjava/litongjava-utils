package com.litongjava.utils.aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtils {
  /**
   * 加密算法
   */
  public static final String KEY_ALGORITHM = "AES";
  /**
   * AES的ECB加密算法
   */
  public static final String ECB_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
  /**
   * AES的CBC加密算法
   */
  public static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

  /**
   * IV(Initialization Value)是一个初始值，对于CBC模式来说，它必须是随机选取并且需要保密的
   * 而且它的长度和密码分组相同(比如：对于AES 128为128位，即长度为16的byte类型数组)
   * 
   */
  public static final byte[] IVPARAMETERS = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };

  /**
   * 使用ECB模式进行加密。 加密过程三步走：
   *  1.传入算法，实例化一个加解密器 
   *  2.传入加密模式和密钥，初始化一个加密器
   *  3.调用doFinal方法加密
   */
  public static byte[] encryptECB(byte[] plainText, SecretKey key) {

    try {

      Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(plainText);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
        | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 使用ECB解密
   */
  public static String decryptECB(byte[] decodedText, SecretKey key) {
    try {
      Cipher cipher = Cipher.getInstance(ECB_CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key);
      return new String(cipher.doFinal(decodedText));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
        | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * CBC加密
   */
  public static byte[] encryptCBC(byte[] plainText, SecretKey key, byte[] IVParameter) {
    try {
      IvParameterSpec ivParameterSpec = new IvParameterSpec(IVParameter);

      Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
      return cipher.doFinal(plainText);

    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
        | IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * CBC 解密
   */
  public static String decreptCBC(byte[] decodedText, SecretKey key, byte[] IVParameter) {
    IvParameterSpec ivParameterSpec = new IvParameterSpec(IVParameter);

    try {
      Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
      return new String(cipher.doFinal(decodedText));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
        | IllegalBlockSizeException | BadPaddingException e) {
      e.printStackTrace();
    }
    return null;

  }

  /**
   * 生成秘钥
   * 1.创建一个KeyGenerator 
   * 2.调用KeyGenerator.generateKey方法
   * @return 秘钥字符串的二进制字节
   */
  public static byte[] generateAESSecretKey() {
    KeyGenerator keyGenerator;
    try {
      keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
      return keyGenerator.generateKey().getEncoded();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static byte[] getKey(String key) {
    KeyGenerator keyGenerator = null;
    try {
      keyGenerator = KeyGenerator.getInstance(key, KEY_ALGORITHM);
      return keyGenerator.generateKey().getEncoded();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 还原密钥
   */
  public static SecretKey restoreSecretKey(byte[] secretBytes) {
    SecretKey secretKey = new SecretKeySpec(secretBytes, KEY_ALGORITHM);
    return secretKey;
  }

  /**
   * 
   * @param cipherBytes 密文
   * @param key 秘钥
   * @param counter 初始化计数器
   * @return
   */
  public static byte[] decryptCTR(byte[] cipherBytes, String key, String counter) {
    // 获取解密密钥  
    byte[] keyBytes = getKey(key);
    Key keySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
    // 获取初始矢量
    byte[] ivBytes = counter.getBytes();
    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    // 获取Cipher实例
    Cipher cipher=null;
    try {
      cipher = Cipher.getInstance("DES/CTR/PKCS5Padding");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    }
    
    // 初始化Cipher实例，设置执行模式，解密密钥和初始计数器
    try {
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
    
    // 解密
    byte[] plainBytes=null;
    try {
      plainBytes = cipher.doFinal(cipherBytes);
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    }
    return plainBytes;
  }

  public static void main(String[] arg) {
    String text = "MANUTD is the greatest club in the world";
    byte[] secretBytes = generateAESSecretKey();
    SecretKey key = restoreSecretKey(secretBytes);
    byte[] encodedText = encryptECB(text.getBytes(), key);

    System.out.println("AES ECB encoded with Base64: " + Base64.encodeBase64String(encodedText));
    System.out.println("AES ECB decoded: " + decryptECB(encodedText, key));

    encodedText = encryptCBC(text.getBytes(), key, IVPARAMETERS);

    System.out.println("AES CBC encoded with Base64: " + Base64.encodeBase64String(encodedText));
    System.out.println("AES CBC decoded: " + decreptCBC(encodedText, key, IVPARAMETERS));
  }
}