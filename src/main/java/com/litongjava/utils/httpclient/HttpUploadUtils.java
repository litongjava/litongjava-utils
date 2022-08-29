package com.litongjava.utils.httpclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * @author litong
 * @date 2019年2月16日_下午3:17:38 
 * @version 1.0
 * @see 02.java模拟post.docx
 * 文件上传工具类 
 */
public class HttpUploadUtils {
  // 换行符
  private static final String newLine = "\r\n";
  private static final String boundaryPrefix = "--";

  /**
   * 上传文件
   * @param localFile
   * @param uploadURL
   * @return
   */
  public static StringBuilder uploadFile(String uploadURL, File localFile, String key) {
    //String path = localFile.getAbsolutePath();
    String filename = localFile.getName();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(localFile);
      return uploadFile(uploadURL, fis, key, filename);
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } finally {
      IOUtils.closeQuietly(fis);
    }
    return null;
  }

  /**
   * 上传文件
   * @param localFilePath
   * @param uploadURL
   * @return
   */
  public static StringBuilder uploadFile(String uploadURL, String localFilePath, String key) {
    File localFile = new File(localFilePath);
    StringBuilder stringBuilder = uploadFile(uploadURL, localFile, key);
    return stringBuilder;
  }

  /**
   * @param uploadURL
   * @param fis
   * @param key 文件上传的key
   * @param filename 上传的文件名
   * @return
   */
  public static StringBuilder uploadFile(String uploadURL, InputStream fis, String key, String filename) {
    // 定义数据分隔线
    String BOUNDARY = "========7d4a6d158c9";
    // 构建文件上传请求体,StringBuilder虽然存在线程不安全问题,但是在该环境下不存
    StringBuilder sb = new StringBuilder();
    sb.append(boundaryPrefix);
    sb.append(BOUNDARY);
    sb.append(newLine);
    // 文件参数,photo参数名可以随意修改
    sb.append("Content-Disposition: form-data;name=\"" + key + "\";filename=\"" + filename + "\"" + newLine);
    sb.append("Content-Type:application/octet-stream");
    // 参数头设置完以后需要两个换行，然后才是参数内容
    sb.append(newLine);
    sb.append(newLine);

    OutputStream out = null;
    DataInputStream in = null;
    HttpURLConnection conn = null;
    try {
      // 服务器的域名
      URL url = new URL(uploadURL);
      conn = (HttpURLConnection) url.openConnection();
      // 设置为POST请求
      conn.setRequestMethod("POST");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      // 设置请求头参数
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("Charsert", "UTF-8");
      conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

      out = new DataOutputStream(conn.getOutputStream());
      // 将参数头的数据写入到输出流中,out输出到url中的部分
      out.write(sb.toString().getBytes());

      // 数据输入流,用于读取文件数据
      in = new DataInputStream(fis);
      byte[] bufferOut = new byte[1024 * 1024 * 5]; // 每次读数据大小
      int size = 0;
      // 将文件数据写入到输出流中
      while ((size = in.read(bufferOut)) != -1) {
        out.write(bufferOut, 0, size);
      }
      // 最后添加换行
      out.write(newLine.getBytes());

      // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
      byte[] endData = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
      // 写上结尾标识
      out.write(endData);
      out.flush(); // 正式上传文件
    } catch (Exception e) {
      System.out.println("发送POST请求出现异常！" + e);
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(out);
    }

    // 定义BufferedReader输入流来读取URL的响应
    BufferedReader reader = null;
    sb.setLength(0); // 清空StringBuffer
    try {
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(reader);
    }
    return sb;
  }
}
