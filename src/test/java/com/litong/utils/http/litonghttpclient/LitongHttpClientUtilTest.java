package com.litong.utils.http.litonghttpclient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.litong.utils.io.IOUtils;
import com.litong.utils.io.StreamUtil;

/**
 * @author bill robot
 * @date 2020年8月22日_上午12:46:18 
 * @version 1.0 
 * @desc
 */
public class LitongHttpClientUtilTest {

  public static void main(String[] args) {
    String filename = "col/col45286/index.html";
    String host = "jshrss.jiangsu.gov.cn/";
    String baseUrl = "http://" + host;
    String targetUrl = baseUrl + filename;
    String folderPath = FilenameUtils.getPath(filename);
    File folderFile = new File(folderPath);
    System.out.println(folderFile.getAbsolutePath());
    if (!folderFile.exists()) {
      folderFile.mkdirs();
    }
    File file = new File(filename);
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Host", host);
    headers.put("Cache-Control", "no-cache");
    headers.put("Accept", "*/*");
    headers.put("Accept-Encoding", "gzip, deflate");
    headers.put("Connection", "keep-alive");
    headers.put("Upgrade-Insecure-Requests", "1");
    headers.put("User-Agent", "PostmanRuntime/7.26.3");
    HttpURLConnection httpURLConnection = null;
    BufferedOutputStream bufferedOutputStream = null;
    InputStream inputStream = null;
    try {
      httpURLConnection = LitongHttpClientUtil.execute(targetUrl, "GET", null, headers);
      Map<String, String> haders = LitongHttpClientUtil.getResponseHeader(httpURLConnection);
      System.out.println(haders);
      bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
      inputStream = httpURLConnection.getInputStream();
      StreamUtil.copy(inputStream, bufferedOutputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      LitongHttpClientUtil.disconnect(httpURLConnection);
      IOUtils.closeQuietly(bufferedOutputStream);
      IOUtils.closeQuietly(inputStream);
    }
  }

}
