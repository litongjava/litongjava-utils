package com.litong.utils.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.litong.utils.io.IOUtils;

/**
 * @author litongjava
 */
public class MailUtil {
  private static final String MAIL_HOST = "smtp.126.com";
  private static final String MAIL_TRANSPORT_PROTOCOL = "smtp";
  private static final String USER = "litongjava_alarm@126.com";
  // 不是密码,是授权码
  private static final String PASSWORD = "g8NmXf6hcy9NexE";

  public static void sendMail(String to, String subject, String content) {
    Properties prop = new Properties();
    // 邮件服务器
    prop.setProperty("mail.host", MAIL_HOST);
    // 传输协议
    prop.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
    // 开启验证
    prop.setProperty("mail.smtp.auth", "true");
    // 获取session
    Session session = Session.getInstance(prop);
    // 开启debug模式时
    session.setDebug(true);
    // 获取 tranport
    Transport ts = null;
    try {
      ts = session.getTransport();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    }
    try {
      ts.connect(MAIL_HOST, USER, PASSWORD);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(USER));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(content);
    } catch (AddressException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    try {
      ts.sendMessage(message, message.getAllRecipients());
    } catch (MessagingException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(ts);
    }

  }

  public static void main(String[] args) {
    try {
      MailUtil.sendMail("litongjava@qq.com", "验证码", "你好,你的验证码是");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
