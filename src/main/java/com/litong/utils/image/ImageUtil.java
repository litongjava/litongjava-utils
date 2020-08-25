package com.litong.utils.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class ImageUtil {

  public static byte[] reduce(byte[] bytes) {
    MemoryCacheImageInputStream inputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(bytes));
    BufferedImage srcImage = null;
    String formatName = null;
    try {
      srcImage = ImageIO.read(inputStream); //读取完成后,inputStream的length变为-1
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

}
