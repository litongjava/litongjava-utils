package com.litongjava.utils.http.litonghttpclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.litongjava.utils.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author litong
 * @desc 文件下载工具类
 */
@Slf4j
public class HttpDownloadUtil {
  public static String downloadFile(String localFilePath, String remoteURL) {
    log.info("开始下载:" + System.currentTimeMillis());
    URL httpURL = null;
    try {
      httpURL = new URL(remoteURL);// 封装成URL
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    HttpURLConnection httpConnection = null;
    try {
      httpConnection = (HttpURLConnection) httpURL.openConnection(); // 打开http连接
    } catch (IOException e) {
      e.printStackTrace();
    }
    httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8"); // 设置请求头,不知道为什么这么设置
    httpConnection.setDoInput(true);
    httpConnection.setDoOutput(true);

    InputStream inputStream = null;
    BufferedInputStream bufferedInputStream = null;
    FileOutputStream fileOutputStream = null;
    BufferedOutputStream bufferedOutputStream = null;
    int length = 0;
    byte[] buffer = new byte[1024 * 1024 * 2]; // 一次读取2M
    try {
      inputStream = httpConnection.getInputStream(); // 获取远程文件流
      bufferedInputStream = new BufferedInputStream(inputStream); // 使用缓冲流

      fileOutputStream = new FileOutputStream(localFilePath);
      bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      while ((length = bufferedInputStream.read(buffer)) != -1) {
        bufferedOutputStream.write(buffer, 0, length); // 将文件保存到outputStream
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(bufferedOutputStream);
      IOUtils.closeQuietly(bufferedInputStream);
    }
    log.info("结束下载:" + System.currentTimeMillis());
    return localFilePath;
  }
}
