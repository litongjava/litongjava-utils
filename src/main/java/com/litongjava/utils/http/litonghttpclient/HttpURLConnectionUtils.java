package com.litongjava.utils.http.litonghttpclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.litongjava.utils.io.IOUtils;
import com.litongjava.utils.io.StreamUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 接口调用工具类 目前支持 GET POST请求两种方式
 */
@Slf4j
public class HttpURLConnectionUtils {

  /**
   * 屏蔽异常
   * @param targetUrl
   * @return
   */
  private static URL initHttpURL(String targetUrl) {
    URL url = null;
    try {
      url = new URL(targetUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

  public static URL initHttpURL(String url, Map<String, Object> param) {
    StringBuffer params = null;
    if (param != null) {
      params = buildHttpQueryParams(param);
      if (params.length() > 0) {
        url += "?" + params.toString();
      }
    }
    return initHttpURL(url);
  }

  /**
   * 获取http connection
   * @param targetUrl
   * @param method
   * @param headers
   * @return
   */
  public static HttpURLConnection getConnection(String targetUrl, String method, Map<String, String> headers) {
    URL initHttpURL = initHttpURL(targetUrl);
    return getConnection(initHttpURL, method, headers);
  }

  /**
   * 获取http connection
   * @param httpURL
   * @param method
   * @param headers
   * @return
   */
  public static HttpURLConnection getConnection(URL httpURL, String method, Map<String, String> headers) {
    HttpURLConnection httpConnection = null;
    try {
      httpConnection = (HttpURLConnection) httpURL.openConnection();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    if (method != null) {
      try {
        httpConnection.setRequestMethod(method);
      } catch (ProtocolException e1) {
        e1.printStackTrace();
      }
    }
    if (headers != null) {
      for (Map.Entry<String, String> e : headers.entrySet()) {
        httpConnection.setRequestProperty(e.getKey(), e.getValue());
      }
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
  public static String get(String url, Map<String, Object> param) {
    return get(url, null, param).getResponse();
  }

  public static HttpResponse get(String targetUrl, Map<String, String> header, Map<String, Object> body) {
    HttpResponse ret = new HttpResponse();
    URL httpURL = initHttpURL(targetUrl, body);
    StringBuffer sb = new StringBuffer();
    try {
      HttpURLConnection httpConnection = (HttpURLConnection) httpURL.openConnection();
      httpConnection.setRequestMethod("GET");
      setHeaders(httpConnection, header);
      int statusCode = httpConnection.getResponseCode();
      ret.setCode(statusCode);
      ret.setHeader(httpConnection.getHeaderFields());
      if (statusCode != 200) {
        if (statusCode == 404) {
          throw new RuntimeException(statusCode+ ":" + httpURL.toString());
        } else {
          throw new RuntimeException("HTTP GET Request Failed with Error code : " + httpConnection.getResponseCode());
        }

      }
      sb = IOUtils.toStringBuffer(httpConnection.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
    ret.setResponse(sb.toString());
    return ret;
  }

  /**
   * 设置请求头
   * @param httpConnection
   * @param header
   */
  public static void setHeaders(HttpURLConnection httpConnection, Map<String, String> header) {
    if (header == null) {
      return;
    }
    for (Map.Entry<String, String> e : header.entrySet()) {
      httpConnection.setRequestProperty(e.getKey(), e.getValue());
    }
  }

  /**
   * 向指定URL发送post请求
   * @throws MalformedURLException 
   */
  public static String post(String url, Map<String, Object> param) {
    return post(url, null, param);
  }

  public static String post(String targetUrl, Map<String, String> headers, Map<String, Object> params) {
    StringBuffer requestString = buildHttpQueryParams(params);
    StringBuffer sb = null;
    // 创建连接
    URL httpURL = initHttpURL(targetUrl);
    HttpURLConnection httpConn = null;
    try {
      httpConn = (HttpURLConnection) httpURL.openConnection();
      if (headers != null) {
        for (Map.Entry<String, String> e : headers.entrySet()) {
          httpConn.setRequestProperty(e.getKey(), e.getValue());
        }
      }

      // 设置参数
      httpConn.setRequestMethod("POST"); // 设置POST方式连接
      setPostConnection(httpConn);
      // 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
      httpConn.connect();

      // 建立输入流，向指向的URL传入参数 ,支持的格式是 key=value&key=value
      DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
      dos.writeBytes(requestString.toString());
      dos.flush();
      dos.close();
      // 读取响应
      if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        sb = IOUtils.toStringBuffer(httpConn.getInputStream());
      } else {
        throw new RuntimeException("HTTP POST Request Failed with Error code : " + httpConn.getResponseCode() + "," + targetUrl);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      httpConn.disconnect();// 断开连接
    }
    return sb.toString();
  }

  @SuppressWarnings("deprecation")
  public static StringBuffer buildHttpQueryParams(Map<String, Object> param) {
    StringBuffer sb = new StringBuffer();
    for (Entry<String, Object> e : param.entrySet()) {
      sb.append(e.getKey() + "=" + URLEncoder.encode(e.getValue().toString()) + "&");
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.lastIndexOf("&"));
    }
    return sb;
  }

  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers, Map<String, Object> body)
      throws MalformedURLException {
    log.info("{}:{}", method, targetUrl);
    if ("GET".equals(method)) {
      return executeGet(targetUrl, method, headers, body);
    } else {
      return executePost(targetUrl, method, headers, body);
    }
  }

  public static HttpURLConnection executeGet(String targetUrl, String method, Map<String, String> headers, Map<String, Object> reqParam) {
    URL httpURL = initHttpURL(targetUrl, reqParam);
    return getConnection(httpURL, method, headers);
  }

  private static HttpURLConnection executePost(String targetUrl, String method, Map<String, String> param, Map<String, Object> headers) {
    return null;
  }

  /**
   * 支持请求参数是values
   * @param targetUrl
   * @param method
   * @param headers
   * @param reqMap
   * @return
   */
  public static HttpURLConnection executeValues(String targetUrl, String method, Map<String, String> headers, Map<String, String[]> reqMap) {
    return null;
  }

  /**
   * @param targetUrl
   * @param method
   * @param headers
   * @param reqInputStream
   * @return
   * @throws MalformedURLException 
   */
  public static HttpURLConnection execute(String targetUrl, String method, Map<String, String> headers, InputStream reqInputStream) {
    HttpURLConnection connection = getConnection(targetUrl, method, headers);
    if ("POST".equals(method)) {
      setPostConnection(connection);
      postStream(connection, reqInputStream);
    }
    return connection;
  }

  private static void postStream(HttpURLConnection connection, InputStream reqInputStream) {
    // 建立输入流，向指向的URL传入参数 ,支持的格式是 key=value&key=value
    DataOutputStream dos = null;
    try {
      dos = new DataOutputStream(connection.getOutputStream());
      StreamUtils.copy(reqInputStream, dos);
      dos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(dos);
    }
  }

  /**
   * 设置post请求必要的练级参数
   * @param connection
   */
  private static void setPostConnection(HttpURLConnection connection) {
    // 设置参数
    connection.setDoOutput(true); // 需要输出
    connection.setDoInput(true); // 需要输入
    connection.setUseCaches(false); // 不允许缓存
    connection.setInstanceFollowRedirects(true); // 设置跟随重定向
  }

  public static void disconnect(HttpURLConnection httpURLConnection) {
    if (httpURLConnection != null) {
      httpURLConnection.disconnect();
    }
  }
}