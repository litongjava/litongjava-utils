package com.litongjava.utils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageLogoUtils {

  /**
   * 创建背景透明的图片
   * 
   * @param width
   * @param height
   * @return
   */
  public static BufferedImage getTransparencyImage(int width, int height) {
    ColorModel cm = ColorModel.getRGBdefault();
    WritableRaster wr = cm.createCompatibleWritableRaster(width, height);
    BufferedImage bufferedImage = new BufferedImage(cm, wr, cm.isAlphaPremultiplied(), null);
    return bufferedImage;
  }

  public static BufferedImage drawLogo(int w, int h, int fontSize, int paddingSize, String s1, String s2, String s3,
      String s4) {
    BufferedImage logo = ImageLogoUtils.getTransparencyImage(w, h);

    // 获取Graphics2D,绘制字体
    Graphics2D g2d = logo.createGraphics();

    Color color = new Color(255, 255, 255);
    // Color color = new Color(255, 0, 0);

    Font font = new Font("微软雅黑", Font.BOLD, fontSize);

    ImageText pinImageText = ImageUtils.createImageText(s1, color, font, paddingSize, paddingSize);
    Graphics2DUtils.drawString(g2d, pinImageText);

    ImageText tuImageText = ImageUtils.createImageText(s2, color, font, w / 2 + paddingSize, paddingSize);
    Graphics2DUtils.drawString(g2d, tuImageText);

    ImageText gongImageText = ImageUtils.createImageText(s3, color, font, paddingSize, h / 2 + paddingSize);
    Graphics2DUtils.drawString(g2d, gongImageText);

    ImageText juImageText = ImageUtils.createImageText(s4, color, font, h / 2 + paddingSize, w / 2 + paddingSize);
    Graphics2DUtils.drawString(g2d, juImageText);

    // 绘制完成,释放资源
    g2d.dispose();

    return logo;

  }
}
