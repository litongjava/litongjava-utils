package com.litongjava.utils.reflection;

import java.io.InputStream;
import java.net.URL;

/**
 * 获取 classpath下的一些路径
 * create by Ping E Lee on 2022年3月30日 下午4:45:16
 *
 */
public class ClassPathResource {

  private String filePath;

  public ClassPathResource(String filePath) {
    this.filePath = filePath;
  }

  public InputStream getInputStream() {
    return this.getClass().getClassLoader().getResourceAsStream(filePath);
  }
  
  public URL getResource() {
    return this.getClass().getClassLoader().getResource(filePath);
  }
}
