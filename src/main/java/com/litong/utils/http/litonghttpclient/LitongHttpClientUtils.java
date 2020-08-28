package com.litong.utils.http.litonghttpclient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Map;

import javax.servlet.ServletInputStream;

/**
 * HttpURLConnection
 * 
 * @author litong
 * @date 2019年6月1日_下午12:46:31
 * @version 1.0
 * @desc
 */
public class LitongHttpClientUtils {
  public static String get(String requestUrl, Map<String, String> data) throws MalformedURLException {
    return HttpURLConnectionUtils.get(requestUrl, data);
  }

  public static String post(String requestUrl, Map<String, String> data) throws MalformedURLException {
    return HttpURLConnectionUtils.post(requestUrl, data);
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers,
      Map<String, String> reqParam) throws MalformedURLException {
    return HttpURLConnectionUtils.execute(targetUrl, method, headers, reqParam);
  }

  public static HttpURLConnection executeValues(String targetUrl, String method, Map<String, String> headers,
      Map<String, String[]> reqMap) {
    return HttpURLConnectionUtils.executeValues(targetUrl, method, headers, reqMap);
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers,
      ServletInputStream reqInputStream) {
    return HttpURLConnectionUtils.execute(targetUrl, method, headers, reqInputStream);
  }

  public static void disconnect(HttpURLConnection httpURLConnection) {
    HttpURLConnectionUtils.disconnect(httpURLConnection);
  }


  public static HttpURLConnection executeGet(String targetUrl, String method, Map<String, String> headers, Map<String,String> requestParams) {
    return HttpURLConnectionUtils.executeGet(targetUrl,method,headers,requestParams);
  }

}
