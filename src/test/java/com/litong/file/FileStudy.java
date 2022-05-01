package com.litong.file;

import java.io.File;
import java.net.URL;

import org.junit.Test;

import com.litongjava.utils.serializable.SerializableUtils;

/**
 * @author litong
 * @date 2019年1月11日_下午3:51:46 
 * @version 1.0 
 */
public class FileStudy {
  /**
   * 如果子文件夹夹不存在,在创建子文件夹
   */
  @Test
  public void study1() {
    String parentPath = "D:\\Program Files";
    String sonPath = "apache-tomcat-8.5.37-1";

    File file = new File(parentPath + File.separator + sonPath);
    if (!file.exists()) {
      System.out.println("创建文件夹");
      file.mkdirs();
    }
  }

  @Test
  public void study2() {
    URL url = this.getClass().getClassLoader().getResource("config-dev.properties");
    System.out.println("url:" + url);
    String filePath = url.getFile();
    File file = new File(filePath);
    System.out.println(file.exists());
    System.out.println("filePath:" + filePath);
  }
  /**
   * url 为null吗?
   */
  @Test
  public void study3() {
    URL url = SerializableUtils.class.getClassLoader().getResource("");
    System.out.println(url);
  }
}
