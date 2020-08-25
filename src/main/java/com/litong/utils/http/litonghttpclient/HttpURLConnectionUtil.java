package com.litong.utils.http.litonghttpclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.litong.utils.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 接口调用工具类 目前支持 GET POST请求两种方式
 */
@Slf4j
public class HttpURLConnectionUtil {

  public static URL initHttpURL(String url, Map<String, String> param) throws MalformedURLException {
    StringBuffer params = null;
    if (param != null) {
      params = buildHttpQueryParams(param);
    }
    if (params != null && params.length() > 0) {
      url += "?" + params.toString();
    }
    return new URL(url);
  }

  public static HttpURLConnection getConnection(URL httpURL, String method, Map<String, String> headers) {
    HttpURLConnection httpConnection = null;
    try {
      httpConnection = (HttpURLConnection) httpURL.openConnection();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    try {
      httpConnection.setRequestMethod(method);
    } catch (ProtocolException e1) {
      e1.printStackTrace();
    }
    for (Map.Entry<String, String> e : headers.entrySet()) {
      httpConnection.setRequestProperty(e.getKey(), e.getValue());
    }
    return httpConnection;
  }

  /**
   * get请求
   * 
   * @param url
   * @param param
   * @return
   * @throws MalformedURLException 
   */
  public static String get(String url, Map<String, String> param) throws MalformedURLException {
    URL httpURL = initHttpURL(url, param);
    StringBuffer sb = new StringBuffer();
    try {
      HttpURLConnection httpConnection = (HttpURLConnection) httpURL.openConnection();
      httpConnection.setRequestMethod("GET");
      httpConnection.setRequestProperty("Accept", "application/json");
      if (httpConnection.getResponseCode() != 200) {
        throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
      }
      sb = IOUtils.toStringBuffer(httpConnection.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  /**
   * 向指定URL发送post请求
   * @throws MalformedURLException 
   */
  public static String post(String url, Map<String, String> param) throws MalformedURLException {
    StringBuffer params = buildHttpQueryParams(param);
    StringBuffer sb = null;
    // 创建连接
    URL httpURL = new URL(url);
    HttpURLConnection httpConn = null;
    try {
      httpConn = (HttpURLConnection) httpURL.openConnection();
      // 设置参数
      httpConn.setDoOutput(true); // 需要输出
      httpConn.setDoInput(true); // 需要输入
      httpConn.setUseCaches(false); // 不允许缓存
      httpConn.setRequestMethod("POST"); // 设置POST方式连接
      httpConn.setInstanceFollowRedirects(true); // 设置跟随重定向
      // 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
      httpConn.connect();

      // 建立输入流，向指向的URL传入参数 ,支持的格式是 key=value&key=value
      DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
      dos.writeBytes(params.toString());
      dos.flush();
      dos.close();
      // 读取响应
      if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        sb = IOUtils.toStringBuffer(httpConn.getInputStream());
      } else {
        throw new RuntimeException("HTTP POST Request Failed with Error code : " + httpConn.getResponseCode() + "," + url);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      httpConn.disconnect();// 断开连接
    }
    return sb.toString();
  }

  @SuppressWarnings("deprecation")
  private static StringBuffer buildHttpQueryParams(Map<String, String> param) {
    StringBuffer sb = new StringBuffer();
    for (Entry<String, String> e : param.entrySet()) {
      sb.append(e.getKey() + "=" + URLEncoder.encode(e.getValue().toString()) + "&");
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.lastIndexOf("&"));
    }
    return sb;
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> param,
      Map<String, String> headers) throws MalformedURLException {
    log.info("{}:{}",method,targetUrl);
    if ("GET".equals(method)) {
      return executeGet(targetUrl, method, param, headers);
    } else {
      return post(targetUrl, method, param, headers);
    }
  }

  private static HttpURLConnection executeGet(String targetUrl, String method, Map<String, String> param,
      Map<String, String> headers) throws MalformedURLException {
    URL httpURL = initHttpURL(targetUrl, param);
    return getConnection(httpURL, method, headers);
  }

  private static HttpURLConnection post(String targetUrl, String method, Map<String, String> param,
      Map<String, String> headers) {
    return null;
  }

  public static void disconnect(HttpURLConnection httpURLConnection) {
    if (httpURLConnection != null) {
      httpURLConnection.disconnect();
    }
  }
}