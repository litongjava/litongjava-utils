package com.litong.utils.file;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2019年1月11日_下午4:42:48 
 * @version 1.0 
 */
public class JSONFileUtilTest {

  @Test
  public void test() {
    User user = new User("litong", "123456");
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter("user.json");
      JSON.writeJSONString(fileWriter, user);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 读取json文件
   */
  @Test
  public void test2() {
  }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
  private String user, pswd;
}
