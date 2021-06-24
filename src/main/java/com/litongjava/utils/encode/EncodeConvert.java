package com.litongjava.utils.encode;

/**
 * @author litong
 * @date 2018年11月25日_上午11:05:02 
 * @version 1.0 
 * 编码转换
 */
public class EncodeConvert {
  /**
   * 如果为空着不进行转换,如果不为空,则转换
   * @param ISO88590Str
   * @return
   */
  public String ISO88591toUTF8(String ISO88591Str) {
    String retval = null;
    if (ISO88591Str == null && "".equals(ISO88591Str)) {
      return null;
    } else {
      try {
        String string = new String(ISO88591Str.getBytes("ISO8859-1"), "ISO8859-1");
        if (ISO88591Str.equals(string)) {
          retval = new String(ISO88591Str.getBytes("ISO8859-1"), "UTF-8");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return retval;
  }
  public static void main(String[] args) {
    EncodeConvert encodeConvert = new EncodeConvert();
    String utf8String = encodeConvert.ISO88591toUTF8("\\u9ED8\\u8BA4\\u7528\\u6237\\u6CE8\\u518C\\u540C\\u65F6\\u6253\\u5F00\\u6240\\u6709\\u9884\\u7F6E\\u670D\\u52A1\\u5F00\\u5173");
    System.out.println(utf8String);
  }
}
