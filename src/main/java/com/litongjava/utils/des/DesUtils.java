package com.litongjava.utils.des;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

public class DesUtils {
  public static final String CHARSET = "utf-8";
  public static String algorithm = "DES";

  public static byte[] encrypt(byte[] plainData, byte[] keyData) throws Exception {

    DESKeySpec dks = new DESKeySpec(keyData);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
    SecretKey key = keyFactory.generateSecret(dks);

    SecureRandom sr = new SecureRandom();

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, sr);
    byte[] cipherData = cipher.doFinal(plainData);
    
    return cipherData;
  }

  public static byte[] decrypt(byte[] cipherData, byte[] keyData) throws Exception {

    DESKeySpec dks = new DESKeySpec(keyData);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
    SecretKey key = keyFactory.generateSecret(dks);

    SecureRandom sr = new SecureRandom();

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, sr);
    byte[] plainData = cipher.doFinal(cipherData);
    return plainData;
  }

  public static String encrypt(String plainText, String keyString) throws Exception {
    byte[] cipherData = encrypt(plainText.getBytes(), keyString.getBytes(CHARSET));
    return new String(Hex.encodeHex(cipherData));
  }

  public static String decrypt(String cipherText, String keyString) throws Exception {
    byte[] cipherData = Hex.decodeHex(cipherText.toCharArray());
    byte[] decrypted = decrypt(cipherData, keyString.getBytes(CHARSET));
    return new String(decrypted, CHARSET);
  }
}