package com.litongjava.utils.reflection;

import java.io.InputStream;

public class ClassPathResource {

  private String filePath;

  public ClassPathResource(String filePath) {
    this.filePath = filePath;
  }

  public InputStream getInputStream() {
    return this.getClass().getClassLoader().getResourceAsStream(filePath);
  }
}
