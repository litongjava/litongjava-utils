package com.litongjava.utils.reflection;

import java.io.InputStream;
import java.net.URL;

/**
 * @author create by Ping E Lee on 2022年3月30日 下午4:48:40 
 *
 */
public class ClassPathUtils {

  public static InputStream getInputStream(String filePath) {
    return ClassPathUtils.class.getClassLoader().getResourceAsStream(filePath);
  }

  public static URL getResourceWithClassLoader(String filePath) {
    return ClassPathUtils.class.getClassLoader().getResource(filePath);
  }
  
  public static URL getResource(String filePath) {
    return ClassPathUtils.class.getResource(filePath);
  }

}
