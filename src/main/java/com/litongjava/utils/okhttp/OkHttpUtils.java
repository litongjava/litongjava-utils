package com.litongjava.utils.okhttp;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ping E Lee on 2018/7/3 16:55
 **/
public class OkHttpUtils {

  private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

  /**
   * 根据map获取get请求参数
   * @param queries
   * @return
   */
  public static StringBuffer getQueryString(String url, Map<String, String> queries) {
    StringBuffer sb = new StringBuffer(url);
    if (queries != null && queries.keySet().size() > 0) {
      boolean firstFlag = true;
      @SuppressWarnings("rawtypes")
      Iterator iterator = queries.entrySet().iterator();
      while (iterator.hasNext()) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map.Entry entry = (Map.Entry<String, String>) iterator.next();
        if (firstFlag) {
          sb.append("?" + entry.getKey() + "=" + entry.getValue());
          firstFlag = false;
        } else {
          sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
      }
    }
    return sb;
  }

  /**
   * 调用okhttp的newCall方法
   * @param request
   * @return
   */
  private static String execNewCall(OkHttpClient okHttpClient,Request request) {
    Response response = null;
    try {
      response = okHttpClient.newCall(request).execute();
      //int status = response.code();
      if (response.isSuccessful()) {
        return response.body().string();
      }
    } catch (Exception e) {
      logger.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
      return null;
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return "";
  }

  /**
   * get
   * @param url     请求的url
   * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
   * @return
   */
  public static String get(OkHttpClient client,String url, Map<String, String> queries) {
    StringBuffer sb = getQueryString(url, queries);
    Request request = new Request.Builder().url(sb.toString()).build();
    return execNewCall(client,request);
  }

  /**
   * post
   *
   * @param url    请求的url
   * @param params post form 提交的参数
   * @return
   */
  public static String postFormParams(OkHttpClient client,String url, Map<String, String> params) {
    FormBody.Builder builder = new FormBody.Builder();
    // 添加参数
    if (params != null && params.keySet().size() > 0) {
      for (String key : params.keySet()) {
        builder.add(key, params.get(key));
      }
    }
    Request request = new Request.Builder().url(url).post(builder.build()).build();
    return execNewCall(client,request);
  }

  /**
   * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
   * 参数一：请求Url
   * 参数二：请求的JSON
   * 参数三：请求回调
   */
  public static String postJsonParams(OkHttpClient client,String url, String jsonParams) {
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
    Request request = new Request.Builder().url(url).post(requestBody).build();
    return execNewCall(client,request);
  }

  /**
   * Post请求发送xml数据....
   * 参数一：请求Url
   * 参数二：请求的xmlString
   * 参数三：请求回调
   */
  public static String postXmlParams(OkHttpClient client,String url, String xml) {
    RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
    Request request = new Request.Builder().url(url).post(requestBody).build();
    return execNewCall(client,request);
  }
}