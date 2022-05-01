package com.litongjava.utils.json;

/**
 * @author Ping E Lee
 *
 */
public class JsonUtils {
  /**
   * 判断是否为json字符串
   * @param jsonString
   * @return
   */
  public static boolean isJsonString(String jsonString) {
    if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
      return true;
    }

    if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
      return true;
    }

    return false;
  }
}
