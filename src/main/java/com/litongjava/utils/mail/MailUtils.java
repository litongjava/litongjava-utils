package com.litongjava.utils.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.litongjava.utils.io.IOUtils;

/**
 * @author litongjava
 */
public class MailUtils {
  private static String MAIL_HOST = "smtp.126.com";
  private static String MAIL_TRANSPORT_PROTOCOL = "smtp";
  private static String USER = "litongjava_alarm@126.com";
  // 不是密码,是授权码
  private static String PASSWORD = "g8NmXf6hcy9NexE";

  public static void set(String mailHost, String protocol, String user, String granTpassword) {
    MAIL_HOST = mailHost;
    MAIL_TRANSPORT_PROTOCOL = protocol;
    USER = user;
    PASSWORD = granTpassword;
  }

  /**
   * 发送邮件
   * @param to 收件人
   * @param subject 主题
   * @param content 内容
   * @paaram isDebug 是否开启debug模式
   */
  public static void sendMail(String to, String subject, String content, boolean isDebug) {
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
    session.setDebug(isDebug);
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
      MailUtils.sendMail("litongjava@qq.com", "验证码", "你好,你的验证码是1234", false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
