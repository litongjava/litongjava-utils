package com.litongjava.utils.sytstem;

public class StrBufUtils {

  public static final String property;
  static {
    property = System.getProperty("line.separator");
  }

  public static void appendln(StringBuffer strBuf, String cmd) {
    strBuf.append(cmd).append(property);
  }
}
