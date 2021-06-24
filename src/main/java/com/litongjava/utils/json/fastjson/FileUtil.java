package com.litongjava.utils.json.fastjson;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.alibaba.fastjson.util.IOUtils;

public class FileUtil {

  /* 写入文件 */
  public static void writeFile(String input, File destination) {

    PrintWriter pw = null;
    try {
      FileWriter fw = new FileWriter(destination);
      pw = new PrintWriter(fw);
      pw.write(input);
      pw.println();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.close(pw);
    }
  }

  /* 读取文件 */
  public static String readFile(File source) {
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = null;

    try {
      InputStream is = new FileInputStream(source);
      reader = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      close(reader);
    }
    return buffer.toString();
  }

  /* 关闭流 */
  public static void close(Closeable x) {
    if (x != null) {
      try {
        x.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}