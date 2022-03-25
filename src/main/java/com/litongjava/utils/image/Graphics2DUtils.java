package com.litongjava.utils.image;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Graphics2DUtils {

  /**
   * 绘制字体
   * 
   * @param g
   * @param imageText
   */
  public static void drawString(Graphics2D g, ImageText imageText) {
    // 获取真实的y坐标值
    Font font = imageText.getFont();
    FontMetrics fontMetrics = g.getFontMetrics(font);
    // 获取字体的Ascent距离
    int ascent = fontMetrics.getAscent();

    int y = imageText.getY();
    int ascenty = imageText.getY() + ascent;

    double chazhi = font.getSize() * 0.23;
    double doubleY = ascenty - chazhi;
    float realy = (float) doubleY;
    log.info("y:{},ascent+y:{},差值:{},realY:{},doubleY-y:{}", y, ascenty, chazhi, realy,doubleY-y);
    
    g.setColor(imageText.getColor()); // 根据图片的背景设置水印颜色
    g.setFont(font); // 设置字体
    // 画出字体
    g.drawString(imageText.getText(), imageText.getX(),realy);
  }
}
