package com.litongjava.utils.audio.mp3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author litong
 * @date 2019年2月13日 上午11:46:31
 * @desc
 */
public class MP3Utils {

  /**
   * 将pcm转为mp3 
   * @param simpleRate 
   */
  public static void pcmToMP3(String pcmFilePath, String mp3FilePath, int simpleRate) {
    int pcmSize = 0; // pcm长度
    byte[] buf = new byte[1024 * 4]; // 一次读取4M
    int size = 0;

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    try {
      fis = new FileInputStream(pcmFilePath);
      bis = new BufferedInputStream(fis);
      size = bis.read(buf); // 将输输入流中的内容读入到buf中,返回读入长度
      /**
       * 取出pcm大小
       */
      while (size != -1) {
        pcmSize += size;
        size = bis.read(buf);
      }
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(fis);
      IOUtils.closeQuietly(bis);
    }

    // 创建WaveHeader
    WaveHeader header = new WaveHeader();
    header.fileLength = pcmSize + (44 - 8);
    header.FmtHdrLeth = 16;
    header.BitsPerSample = 16;
    header.Channels = 1;
    header.FormatTag = 0x0001;
    header.SamplesPerSec = simpleRate;
    header.BlockAlign = (short) (header.Channels * header.BitsPerSample / 8);
    header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
    header.DataHdrLeth = pcmSize;
    byte[] h = header.getHeader();

    assert h.length == 44;

    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    try {
      fos = new FileOutputStream(mp3FilePath);
      bos = new BufferedOutputStream(fos);
      fis = new FileInputStream(pcmFilePath);
      bis = new BufferedInputStream(fis);

      bos.write(h, 0, h.length); // 写入文件头部
      size = fis.read(buf);
      while (size != -1) { // 写入文件
        bos.write(buf, 0, size);
        size = fis.read(buf);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(fos);
      IOUtils.closeQuietly(bos);
    }
  }
}
