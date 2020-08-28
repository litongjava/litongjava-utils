package com.litong.utils.http.litonghttpclient;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author bill robot
 * @date 2020年8月26日_下午4:57:35 
 * @version 1.0 
 * @desc
 */
public class LitongHttpClientUtilsTest {

  /**
   * 测试disconnect后getInputStream有没有问题
   */
  @Test
  public void test() {
    // https://www.ximalaya.com/,GET,{accept-language=zh-CN,zh;q=0.9,en;q=0.8,
    // host=www.ximalaya.com, upgrade-insecure-requests=1, connection=keep-alive,
    // accept-encoding=gzip, deflate, user-agent=Mozilla/5.0 (Windows NT 10.0;
    // Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116
    // Safari/537.36,
    // accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9}
    String targetUrl = "https://www.ximalaya.com/";
    String method = "GET";
    Map<String, String> headers = new HashMap<>();
    headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
    headers.put("host", "www.ximalaya.com");
    headers.put("upgrade-insecure-requests", "1");
    headers.put("connection", "keep-alive");
    headers.put("user-agent",getUserAggent());
        
    headers.put("cache-control", "zh-CN,zh;q=0.9,en;q=0.8");
    headers.put("pragma", "no-cache");
    headers.put("accept-encoding", "gzip,deflate");
    
    headers.put("accept",
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
    HttpURLConnection connection = HttpClientUtils.executeGet(targetUrl, method, headers, null);
    HttpClientUtils.disconnect(connection);
    Map<String, List<String>> headerFields = connection.getHeaderFields();
    for (Map.Entry<String, List<String>> e : headerFields.entrySet()) {
      System.out.println(e.getKey());
      System.out.println(e.getValue());
    }

  }

  private String getUserAggent() {
    return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
  }

}
