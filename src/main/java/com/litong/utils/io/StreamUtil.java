package com.litong.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author bill robot
 * @date 2020年8月22日_上午1:08:25 
 * @version 1.0 
 * @desc
 */
public class StreamUtil {

  public static final int BUFFER_SIZE = 4096;

  public static int copy(InputStream in, OutputStream out) throws IOException{
    int byteCount = 0;
    byte[] buffer = new byte[BUFFER_SIZE];
    int bytesRead = -1;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
      byteCount += bytesRead;
    }
    out.flush();
    return byteCount;
  }

}
