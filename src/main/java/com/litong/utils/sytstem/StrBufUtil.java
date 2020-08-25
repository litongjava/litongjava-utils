package com.litong.utils.sytstem;

public class StrBufUtil {

  public static void appendln(StringBuffer strBuf, String cmd) {
    strBuf.append(cmd).append(System.getProperty("line.separator"));
  }
}
