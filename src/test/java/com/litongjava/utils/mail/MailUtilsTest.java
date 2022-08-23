package com.litongjava.utils.mail;

import org.junit.Test;

import com.litongjava.utils.vo.JsonBean;

/**
 * @author Ping E Lee
 *
 */
public class MailUtilsTest {

  @Test
  public void testSendMail() {
    JsonBean<String> jsonBean = new JsonBean<>();
    try {
      String to="litonglinux@qq.com";
      String subject="注册验证码";
      String content="你的注册验证码是12456";
      MailUtils.sendMail(to, subject, content, false);
      jsonBean.setData("success");
    } catch (Exception e) {
      jsonBean.setCode(-1);
      jsonBean.setMsg(e.getMessage());
      e.printStackTrace();
    }
    
    System.out.println(jsonBean);
  }

}
