package com.litongjava.utils.icepdf;

import java.io.File;
import java.util.List;

import org.junit.Test;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class IcePdfUtilsTest {

  @Test
  public void test() {
    String pdfPath="D:/code/project/project-ppnt/ppnt-ocr/src/main/webapp/upload/4fbf1424615740978cefd86fe4895ca0.pdf";
    List<File> images = IcePdfUtils.toImages(pdfPath, 5f,false);
    images.forEach(System.out::println);
  }

}
