package com.litongjava.utils.json.fastjson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public class FastJsonUtils {

  /**
   * 写入文件
   * 
   * @param filename
   * @param object
   * @throws IOException
   */
  public static void writeToFile(String filename, Object object) throws IOException {
    try (FileOutputStream out = new FileOutputStream(filename);) {
     //JSON.writeJSONString(bufferedWriter, object);
      JSON.writeTo(out, object, JSONWriter.Feature.PrettyFormat);
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
