package com.litongjava.utils.image;

import java.awt.Color;
import java.awt.Font;

import lombok.Data;

@Data
public class ImageText {
  // 文字内容
  private String text;
  // 字体颜色和透明度
  private Color color;
  // 字体和大小
  private Font font;
  // 所在图片的x坐标
  private int x;
  // 所在图片的y坐标
  private int y;
}
