package com.litong.utils.integer;

/**
 * @author litong
 * @date 2019年4月19日_下午1:31:02
 * @version 1.0
 * @desc 16进制转换工具类;
 */
public class HexUtils {

  /**
   * convert byte to hex string
   * 
   * @param b
   * @return
   */
  public static StringBuffer toHexString(byte b) {
    StringBuffer stringBuilder = new StringBuffer();
    int temp = b & 0xFF;
    String hexString = Integer.toHexString(temp);
    if (hexString.length() < 2) {
      stringBuilder.append(0);
    }
    stringBuilder.append(hexString);
    return stringBuilder;
  }

  /**
   * Convert byte[] to hex string
   */
  public static String toHexString(byte[] src) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (src == null || src.length <= 0) {
      return null;
    }
    for (int i = 0; i < src.length; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }

  /**
   * Convert hex string to byte[]
   */
  public static byte[] toBytes(String hex) {
    int len = (hex.length() / 2);
    byte[] result = new byte[len];
    char[] achar = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
    }
    return result;
  }

  /**
   * Convert char to byte
   */
  public static byte toByte(char c) {
    byte b = (byte) "0123456789abcdef".indexOf(c);
    return b;
  }

  /**
   * 
   * @param key
   * @return
   */
  public static byte[] toUnsignedBytes(String key) {
    return null;
  }

  public static String toUnsignedHexString(byte[] src) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < src.length; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toUnsignedString(v);
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }
}
