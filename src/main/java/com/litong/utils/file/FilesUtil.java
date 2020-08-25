package com.litong.utils.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author 李通
 * @date 2019年1月8日_下午2:47:44 
 * @version 1.0 
 */
public class FilesUtil {
  public String readFile(String filepath) {
    StringBuffer stringBuffer = new StringBuffer();
    List<String> readAllLines = null;
    try {
      readAllLines = Files.readAllLines(Paths.get(filepath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (String string : readAllLines) {
      stringBuffer.append(string);
    }
    return stringBuffer.toString();
  }
}
