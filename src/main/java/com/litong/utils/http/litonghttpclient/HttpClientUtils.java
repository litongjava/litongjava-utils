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
public class HttpClientUtils {
  public static String get(String targetUrl, Map<String,Object> data){
    return HttpURLConnectionUtils.get(targetUrl, data);
  }

  public static String post(String targetUrl, Map<String, Object> data) throws MalformedURLException {
    return HttpURLConnectionUtils.post(targetUrl, data);
  }

  public static String post(String targetUrl, Map<String, String> headers, Map<String, Object> params) {
    return HttpURLConnectionUtils.post(targetUrl, headers, params);
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers, Map<String, Object> reqParam)
      throws MalformedURLException {
    return HttpURLConnectionUtils.execute(targetUrl, method, headers, reqParam);
  }

  public static HttpURLConnection executeValues(String targetUrl, String method, Map<String, String> headers, Map<String, String[]> reqMap) {
    return HttpURLConnectionUtils.executeValues(targetUrl, method, headers, reqMap);
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers, ServletInputStream reqInputStream) {
    return HttpURLConnectionUtils.execute(targetUrl, method, headers, reqInputStream);
  }

  public static void disconnect(HttpURLConnection httpURLConnection) {
    HttpURLConnectionUtils.disconnect(httpURLConnection);
  }

  public static HttpURLConnection executeGet(String targetUrl, String method, Map<String, String> headers, Map<String, Object> requestParams) {
    return HttpURLConnectionUtils.executeGet(targetUrl, method, headers, requestParams);
  }

  public static StringBuffer buildHttpQueryParams(Map<String, Object> params) {
    return HttpURLConnectionUtils.buildHttpQueryParams(params);
  }

  public static HttpResponse get(String targetUrl, Map<String, String> header, Map<String, Object> body) {
    return HttpURLConnectionUtils.get(targetUrl,header,body);
  }

}
