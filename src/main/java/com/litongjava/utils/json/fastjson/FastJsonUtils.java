package com.litongjava.utils.json.fastjson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

public class FastJsonUtils {

  /**
   * 写入文件
   * 
   * @param filename
   * @param object
   * @throws IOException
   */
  public static void writeToFile(String filename, Object object) throws IOException {
    File file = new File(filename);
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {
      JSON.writeJSONString(bufferedWriter, object);
    }
  }
  
  /**
   * 读取JSON内容到list
   * @param <T>
   * @param filename
   * @param clazz
   * @return
   * @throws IOException
   */
  public static <T> List<T> readFileToList(String filename,Class<T> clazz) throws IOException{
    File file = new File(filename);
    String jsonString = FileUtils.readFileToString(file,Charset.defaultCharset());
    List<T> parseArray = JSON.parseArray(jsonString, clazz);
    return parseArray;
  }
}
