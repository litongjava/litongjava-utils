package com.litong.utils.reflection;

import java.util.Map;

import org.junit.Test;

import com.litong.utils.poi.User;

/**
 * @author bill robot
 * @date 2020年6月15日_下午11:48:35 
 * @version 1.0 
 * @desc
 */
public class LReflectionUtilsTest {

  @Test
  public void test() {
    User build = new User().build();
    System.out.println(build);
    Map<String, Object> convertObjectToMap = LReflectionUtils.convertObjectToMap(build);
    System.out.println(convertObjectToMap);
  }
}
