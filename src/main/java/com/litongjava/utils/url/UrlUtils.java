package com.litongjava.utils.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author litong
 * @date 2018年12月7日_下午5:12:12 
 * @version 1.0 
 */
public class UrlUtils {
  /**
   * 获取网络图片流
   * 
   * @param url
   * @return
   */
  public InputStream getImageStream(String url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setReadTimeout(5000);
      connection.setConnectTimeout(5000);
      connection.setRequestMethod("GET");
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream inputStream = connection.getInputStream();
        return inputStream;
      }
    } catch (IOException e) {
      System.out.println("获取网络图片出现异常，图片路径为：" + url);
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 另一种方法,获取图片流
   * @param url
   * @return
   */
  public InputStream getImageStream0(String url) {
    URL url2 = null;
    try {
      url2 = new URL(url);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    InputStream inputStream = null;
    try {
      inputStream = url2.openStream();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return inputStream;
  }

  /**
   * 拼接2个url或者2个路径和名和文件名
   * @param url1
   * @param url2
   * @return
   */
  public static StringBuffer append(String url1, String url2) {
    StringBuffer stringBuffer = new StringBuffer();
    if (url1.endsWith("/") && url2.startsWith("/")) {
      // http://www.baidu.com/ /query
      stringBuffer.append(url1);
      // 删除url1最后一个字符
      stringBuffer.deleteCharAt(stringBuffer.length() - 1);
      stringBuffer.append(url2);
    } else if (url1.endsWith("/") && !url2.startsWith("/")) {
      // http://www.baidu.com/ query
      stringBuffer.append(url1).append(url2);
    } else if (!url1.endsWith("/") && url2.startsWith("/")) {
      // http://www.baidu.com /query
      stringBuffer.append(url1).append(url2);
    } else if (!url1.endsWith("/") && !url2.startsWith("/")) {
      stringBuffer.append(url1).append("/").append(url2);
    }
    return stringBuffer;
  }
}
