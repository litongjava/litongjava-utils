package com.litong.utils.serializable;

import java.io.Serializable;

import org.junit.Test;

import com.litongjava.utils.serializable.SerializableUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2019年1月11日_下午3:34:16 
 * @version 1.0 
 */
public class SerializableUtilTest {

  @Test
  public void test() {
    User user = new User("litong","123456");
    SerializableUtils.writeToFile(user, "user.serializable");
  }

}
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
class User implements Serializable{
  private String user,pswd;
}
