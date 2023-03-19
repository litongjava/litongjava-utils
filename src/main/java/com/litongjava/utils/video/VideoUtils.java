package com.litongjava.utils.video;
import java.io.IOException;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.litongjava.utils.audio.AudioUtils;

public class VideoUtils {

  /**
   * 获取视频文件的播放长度(mp4、mov格式)
   * @param videoPath
   * @return 单位为秒
   */
  public static long getMp4Duration(String videoPath) throws IOException {
    //isoFile
    IsoFile isoFile = new IsoFile(videoPath);
    //movieBox
    MovieBox movieBox = isoFile.getMovieBox();
    //mavieHeaderBox
    MovieHeaderBox movieHeaderBox = movieBox.getMovieHeaderBox();
    //duration
    long duration = movieHeaderBox.getDuration();
    //timescale
    long timescale = movieHeaderBox.getTimescale();
    isoFile.close();
    long lengthInSeconds = duration / timescale;
    return lengthInSeconds;
  }

  /**
   * 得到语音或视频文件时长,单位秒
   * @param filePath
   * @return
   * @throws IOException
   */
  public static long getDuration(String filePath) throws IOException {
    String format = getVideoFormat(filePath);
    long result = 0;
    if ("wav".equals(format)) {
      result = AudioUtils.getDuration(filePath).intValue();
    } else if ("mp3".equals(format)) {
      result = AudioUtils.getMp3Duration(filePath).intValue();
    } else if ("m4a".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    } else if ("mov".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    } else if ("mp4".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    }

    return result;
  }

  /**
   * 得到语音或视频文件时长,单位秒
   * @param filePath
   * @return
   * @throws IOException
   */
  public static long getDuration(String filePath, String format) throws IOException {
    long result = 0;
    if ("wav".equals(format)) {
      result = AudioUtils.getDuration(filePath).intValue();
    } else if ("mp3".equals(format)) {
      result = AudioUtils.getMp3Duration(filePath).intValue();
    } else if ("m4a".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    } else if ("mov".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    } else if ("mp4".equals(format)) {
      result = VideoUtils.getMp4Duration(filePath);
    }
    return result;
  }

  /**
   * 得到文件格式
   * @param path
   * @return
   */
  public static String getVideoFormat(String path) {
    String lowerCase = path.toLowerCase();
    return lowerCase.substring(lowerCase.lastIndexOf(".") + 1);
  }
}