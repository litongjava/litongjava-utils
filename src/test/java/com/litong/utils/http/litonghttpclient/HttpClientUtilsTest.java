package com.litong.utils.http.litonghttpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.litongjava.utils.http.litonghttpclient.HttpClientUtils;

/**
 * @author create by ping-e-lee on 2021年3月19日 下午4:08:55 
 * @version 1.0 
 * @desc
 */
public class HttpClientUtilsTest {

  @Test
  public void testPost() {
    String targetUrl = "http://192.168.11.191/robot/app/hnzj/ask.action";
    Map<String, Object> body = new HashMap< >();
    body.put("platform", "web");
    body.put("question", "宿舍锁门时间");
    String ret=HttpClientUtils.post(targetUrl, null, body);
    System.out.println(ret);
  }
  
  @Test
  public void urlEncode() {
    String encode=null;
    try {
      encode = URLEncoder.encode("宿舍锁门时间", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    System.out.println(encode);
  }

}
