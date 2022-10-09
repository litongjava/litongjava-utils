package com.litong.utils.file;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.litongjava.utils.io.IOUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litongjava@qq.com
 * @date 2019年1月11日_下午4:42:48 
 * @version 1.0 
 */
public class JSONFileUtilTest {

  @Test
  public void test() {
    User user = new User("litong", "123456");
    FileOutputStream out = null;
    try {
      //fileWriter = new FileWriter("user.json");
      //JSON.writeJSONString(fileWriter, user);
      out = new FileOutputStream("user.json");
      JSON.writeTo(out, user, JSONWriter.Feature.PrettyFormat);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(out);
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
