package com.litongjava.utils.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtils {

  public static byte[] reduce(byte[] bytes) {
    MemoryCacheImageInputStream inputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(bytes));
    BufferedImage srcImage = null;
    String formatName = null;
    try {
      srcImage = ImageIO.read(inputStream); // 读取完成后,inputStream的length变为-1
      formatName = getImageFormatByBytes(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(inputStream);
    }

    AffineTransformOp ato = getAffineTransformOp(0.5);
    // 过滤,进行图片缩放
    BufferedImage destImage = ato.filter(srcImage, null);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    byte[] retval = null;
    try {
      ImageIO.write(destImage, formatName, outputStream);
      retval = outputStream.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(outputStream);
    }
    return retval;
  }

  /**
   * 获取图片流对应的图片编码格式
   * 
   * @param content
   * @return
   */
  public static String getImageFormatByBytes(byte[] content) {
    ImageInputStream input = null;
    try {
      input = new MemoryCacheImageInputStream(new ByteArrayInputStream(content));
      return getImageFormat(input);
    } finally {
      IOUtils.closeQuietly(input);
    }
  }

  // this function will not close input, need caller to close it.
  public static String getImageFormat(ImageInputStream input) {
    Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
    String format = "unknown";
    if (readers.hasNext()) {
      ImageReader reader = readers.next();
      try {
        format = reader.getFormatName();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        reader.dispose();
      }
    }
    return format;
  }

  private static AffineTransformOp getAffineTransformOp(double d) {
    // 模变换,设置缩放比例
    AffineTransform scaleInstance = AffineTransform.getScaleInstance(d, d);
    // 仿射变换
    return new AffineTransformOp(scaleInstance, null);
  }

  /**
   * 导入本地图片到缓冲区
   */
  public static BufferedImage load(String imgName) {
    try {
      return ImageIO.read(new File(imgName));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public static BufferedImage merge(BufferedImage image1, BufferedImage image2) {

    int w = image1.getWidth() + image2.getWidth();
    int h = image1.getHeight();

    // BufferedImage.TYPE_3BYTE_BGR-->jpg
    BufferedImage retval = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

    int image1Width = image1.getWidth();

    Graphics2D g = retval.createGraphics();
    g.drawImage(image1, 0, 0, image1.getWidth(), h, null);
    g.drawImage(image2, image1Width, 0, image2.getWidth(), h, null);
    g.dispose();

    return retval;
  }

  /**
   * 生成新图片到本地
   */
  public static void write(String targetImgPath, BufferedImage img) {
    write(targetImgPath, "jpg", img);
  }

  /**
   * 生成新图片到本地
   */
  public static void write(String targetImgPath, String format, BufferedImage img) {
    if (targetImgPath != null && img != null) {
      try {
        File outputfile = new File(targetImgPath);
        ImageIO.write(img, format, outputfile);
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void writeImage(BufferedImage bufImg, String tarImgPath, ArrayList<ImageText> list) {
    // 创建目录图片的BufferImage
    // 获取Graphics2D
    Graphics2D g = bufImg.createGraphics();
    // 将原图添加到BufferImage
    for (ImageText imageText : list) {
      g.setColor(imageText.getColor()); // 根据图片的背景设置水印颜色
      g.setFont(imageText.getFont()); // 设置字体
      g.drawString(imageText.getText(), imageText.getX(), imageText.getY()); // 画出水印
    }
    g.dispose();
    write(tarImgPath, bufImg);
  }

  /**
   * 编辑图片,往指定位置添加文字
   * 
   * @param srcImgPath    :源图片路径
   * @param targetImgPath :保存图片路径
   * @param list          :文字集合
   */
  public static void writeImage(String srcImgPath, String targetImgPath, List<ImageText> list) {
    FileOutputStream outImgStream = null;

    File srcImgFile = new File(srcImgPath);// 得到文件

    try {
      // 读取原图片信息
      Image srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
      int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
      int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
      // 创建目录图片的BufferImage
      BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
      // 获取Graphics2D
      Graphics2D g = bufImg.createGraphics();
      // 将原图添加到BufferImage
      g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);

      for (ImageText imageText : list) {
        g.setColor(imageText.getColor()); // 根据图片的背景设置水印颜色
        g.setFont(imageText.getFont()); // 设置字体
        g.drawString(imageText.getText(), imageText.getX(), imageText.getY()); // 画出水印
      }
      g.dispose();

      // 输出图片
      outImgStream = new FileOutputStream(targetImgPath);
      ImageIO.write(bufImg, "jpg", outImgStream);
    } catch (Exception e) {
      log.error("==== 系统异常::{} ====", e);
    } finally {
      try {
        if (null != outImgStream) {
          outImgStream.flush();
          outImgStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 创建ImageText, 每一个对象,代表在该图片中要插入的一段文字内容:
   * 
   * @param text  : 文本内容;
   * @param color : 字体颜色(前三位)和透明度(第4位,值越小,越透明);
   * @param font  : 字体的样式和字体大小;
   * @param x     : 当前字体在该图片位置的横坐标;
   * @param y     : 当前字体在该图片位置的纵坐标;
   * @return
   */
  public static ImageText createImageText(String text, Color color, Font font, int x, int y) {
    ImageText imageText = new ImageText();
    imageText.setText(text);
    imageText.setColor(color);
    imageText.setFont(font);
    imageText.setX(x);
    imageText.setY(y);
    return imageText;
  }

  public static BufferedImage merge(String srcImagePath1, String srcImagePath2) {
    BufferedImage image1 = load(srcImagePath1);
    BufferedImage image2 = load(srcImagePath2);
    return merge(image1, image2);
  }
}
