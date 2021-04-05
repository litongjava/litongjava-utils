package test.java.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * @author create by ping-e-lee on 2021年3月18日 下午10:14:33 
 * @version 1.0 
 * @desc
 */
public class URLEncoderTest {

  @Test
  public void encode() throws UnsupportedEncodingException {
    String encode = URLEncoder.encode("李通", "UTF-8");
    System.out.println(encode);
  }

}
