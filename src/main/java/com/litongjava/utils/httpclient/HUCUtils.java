package com.litongjava.utils.httpclient;

import java.util.Map;

/**
 * HttpURLConnection
 * 
 * @author litong
 * @date 2019年6月1日_下午12:46:31
 * @version 1.0
 * @desc
 */
public class HUCUtils {
  public static String get(String requestUrl, Map<String, Object> data) {
    return HttpURLConnectionUtils.get(requestUrl, data);
  }

  public static String post(String requestUrl, Map<String, Object> data) {
    return HttpURLConnectionUtils.post(requestUrl, data);
  }
}
