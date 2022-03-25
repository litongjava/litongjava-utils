package com.litongjava.utils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageLogoUtilsTest {

  @Test
  public void testDrawLogo() {
    // 绘制logo
    int w = 512, h = 512;
    int fontSize = 200;
    int paddingSize = 29;
    BufferedImage logo = ImageLogoUtils.drawLogo(w, h, fontSize, paddingSize, "拼", "图", "工", "具");
    // 保存图片
    ImageUtils.write("ic_launcher_hnzjimagetools-" + paddingSize + "_" + fontSize + ".png", "png", logo);
  }

  @Test
  public void testGetTransparencyImage() {
    BufferedImage transparencyImage = ImageLogoUtils.getTransparencyImage(512, 512);
    ImageUtils.write("ic_launchar_hnzjimagetools.png", "png", transparencyImage);
  }

  @Test
  public void testGetWriterFormatNames() {
    String[] writerFormatNames = ImageIO.getWriterFormatNames();
    for (String string : writerFormatNames) {
      System.out.println(string);
    }
  }

}
