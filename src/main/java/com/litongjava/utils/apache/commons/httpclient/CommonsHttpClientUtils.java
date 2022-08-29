package com.litongjava.utils.apache.commons.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.litongjava.utils.string.StringUtils;

/**
 * Created by litong on 2019/1/3 0003.
 * 
 * @See 02.commons-httpclient.docx 基于commons-httpclient封装的工类
 */
public class CommonsHttpClientUtils {
  private static HttpClient httpClient;
  static {
    httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
    httpClient.getParams().setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, true);
  }

  public static boolean debug = false;
  // private boolean isDevelopment = false;

  /**
   * 执行get请求
   */
  public static String get(String url) {
    String s = get(url, null);
    return s;
  }

  public static String get(String url, Map<String, String> params) {
    GetMethod getMethod = null;
    String retval = null;
    if (params == null) {
      getMethod = new GetMethod(url);
    } else { // 拼接成长串
      url = appendURL(url, params);
      if (debug) {
        System.out.println(url);
      }
      getMethod = new GetMethod(url);
    }
    retval = executeMethod(getMethod);
    return retval;
  }

  /**
   * 拼接URL和Map
   * 
   * @return
   */
  public static String appendURL(String url, Map<String, String> params) {
    StringBuffer stringBuffer = new StringBuffer();
    for (Map.Entry<String, String> e : params.entrySet()) {
      stringBuffer.append("&");
      try {
        stringBuffer.append(e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8"));
      } catch (UnsupportedEncodingException e1) {
        e1.printStackTrace();
      }
    }
    if (url.contains("?") /* || url.endsWith("?") */) { // URL中已经包含Key和Value
      url += stringBuffer.toString();
    } else {
      url = url + "?" + stringBuffer.toString();
    }
    return url;
  }

  private static String executeMethod(HttpMethod method) {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      httpClient.executeMethod(method);
      InputStream inputStream = method.getResponseBodyAsStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

      String str = null;
      while ((str = br.readLine()) != null) {
        stringBuffer.append(str);
      }
    } catch (IOException e) {
      try {
        System.err.println(method.getURI().toString());
      } catch (URIException e1) {
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    return stringBuffer.toString();
  }

  /**
   * 执行post请求
   */
  public static String post(String url, Map<String, String> header, Map<String, String> data) {
    // PostMethod postMethod = getPostMethod(url);
    PostMethod postMethod = new PostMethod(url);
    postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8"); // 设置编码为UTF-8
    if (header != null) {
      for (Map.Entry<String, String> e : header.entrySet()) {
        postMethod.setRequestHeader(e.getKey(), e.getValue());
      }
    }
    if (data != null) {
      for (Map.Entry<String, String> e : data.entrySet()) {
        if (!StringUtils.isEmpty(e.getValue())) {
          postMethod.setParameter(e.getKey(), e.getValue());
        }
      }
    }
    String retval = executeMethod(postMethod);
    postMethod.releaseConnection();
    return retval;
  }

  /**
   * 发送post-json 将形参data转为json
   */
  public static String postJSON(String url, Map<String, String> data, String encoding) {
    String jsonString = JSON.toJSONString(data);
    String retval = postJSON(url, jsonString, encoding);
    return retval;
  }

  /**
   * 发送post-json
   */
  public static String postJSON(String url, String jsonString, String encoding) {
    PostMethod postMethod = new PostMethod(url);
    try {
      StringRequestEntity requestEntity = new StringRequestEntity(jsonString, "application/json", encoding);
      postMethod.setRequestEntity(requestEntity);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String retval = executeMethod(postMethod);
    return retval;
  }
}