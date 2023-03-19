package com.litongjava.utils.mail;

import org.junit.Test;


/**
 * @author Ping E Lee
 *
 */
public class MailUtilsTest {

  @Test
  public void testSendMail() {
    try {
      String to="litonglinux@qq.com";
      String subject="注册验证码";
      String content="你的注册验证码是12456";
      MailUtils.sendMail(to, subject, content, false);  
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

}
