package com.litong.utils.url;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * @author litong
 * @date 2018年12月7日_下午5:15:02 
 * @version 1.0 
 */
public class URLUtilTest {

  @Test
  public void test() {
    fail("Not yet implemented");
  }

  @Test
  public void getImageStream() {
    String url = "https://fanyi.bdstatic.com/static/translation/img/header/logo_cbfea26.png";
    URLUtil urlUtil = new URLUtil();
    InputStream imageStream = urlUtil.getImageStream(url);
    try {
      FileUtils.copyInputStreamToFile(imageStream, new File("log-001.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getImageStream0() {

    String url = "https://fanyi.bdstatic.com/static/translation/img/header/logo_cbfea26.png";
    URLUtil urlUtil = new URLUtil();
    InputStream imageStream0 = urlUtil.getImageStream0(url);
    try {
      FileUtils.copyToFile(imageStream0, new File("logo-002.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void copyURLToFile() throws IOException {
    String pngUrl = "https://fanyi.bdstatic.com/static/translation/img/header/logo_cbfea26.png";
    URL netUrl = new URL(pngUrl);
    InputStream openStream = netUrl.openStream();
    FileUtils.copyInputStreamToFile(openStream, new File("logo-004.png"));
  }
}
