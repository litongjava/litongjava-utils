package com.litongjava.utils.icepdf;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

/**
 * @author litongjava <litongjava@qq.com>
 *
 */
public class IcePdfUtils {

  public static List<File> toImages(String pdfPath, float scale) {
    Document document = new Document();
    try {
      document.setFile(pdfPath);
    } catch (PDFException | PDFSecurityException | IOException e1) {
      e1.printStackTrace();
      return null;
    }
    List<File> retval = new ArrayList<>();
    // 测试结果,经过5倍放大后可以看清字体
    // float scale = 5f;// 缩放比例
    float rotation = 0f;// 旋转角度

    for (int i = 0; i < document.getNumberOfPages(); i++) {
      // getPageImage
      BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
          //
          org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);

      RenderedImage rendImage = image;

      String num = i < 9 ? "0" + (1+1) : (i+1) + "";
      File file = new File(pdfPath + "_" + num + ".png");
      try {
        ImageIO.write(rendImage, "png", file);
        retval.add(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
      image.flush();
    }
    document.dispose();
    return retval;
  }
}
