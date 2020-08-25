package com.litong.utils.http.litonghttpclient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Map;

/**
 * HttpURLConnection
 * 
 * @author litong
 * @date 2019年6月1日_下午12:46:31
 * @version 1.0
 * @desc
 */
public class LitongHttpClientUtil {
  public static String get(String requestUrl, Map<String, String> data) throws MalformedURLException {
    return HttpURLConnectionUtil.get(requestUrl, data);
  }

  public static String post(String requestUrl, Map<String, String> data) throws MalformedURLException {
    return HttpURLConnectionUtil.post(requestUrl, data);
  }

  public static HttpURLConnection execute(String targetUrl, String type, Map<String, String> param, Map<String, String> headers) throws MalformedURLException {
    return HttpURLConnectionUtil.execute(targetUrl, type, param, headers);
  }

  public static void disconnect(HttpURLConnection httpURLConnection) {
    HttpURLConnectionUtil.disconnect(httpURLConnection);
  }

  public static Map<String, String> getResponseHeader(HttpURLConnection httpURLConnection) {
    return null;
  }
}
