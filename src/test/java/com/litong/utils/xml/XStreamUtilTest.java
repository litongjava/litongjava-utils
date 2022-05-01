package com.litong.utils.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.litongjava.utils.xml.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litong
 * @date 2018年12月4日_下午8:17:47 
 * @version 1.0 
 */
public class XStreamUtilTest {

  @Test
  public void test() {
    User user = new User("username", "password");
    String beanToXML = XStreamUtils.beanToXML(user);
    System.out.println(beanToXML);
  }

  @Test
  public void xmlToBean() {
    String xml = "<user><username>username</username><password>password</password></user>";
    User xmlToBean = XStreamUtils.XMLToBean(xml, User.class);
    System.out.println(xmlToBean);
  }

  /**
   * 单图文
   */
  @Test
  public void itemToXML() {
    ImgTxtMsg imgTxtMsg1 = new ImgTxtMsg();
    imgTxtMsg1.setTitle("标题1");
    imgTxtMsg1.setDescription("描述1");
    imgTxtMsg1.setPicUrl("pic url1");
    imgTxtMsg1.setUrl("url1");
    String beanToXML = XStreamUtils.beanToXML(imgTxtMsg1);
    System.out.println(beanToXML);
  }

  @Test
  public void item2ToXML() {
    ImgTxtMsg imgTxtMsg1 = new ImgTxtMsg();
    imgTxtMsg1.setTitle("标题1");
    imgTxtMsg1.setDescription("描述1");
    imgTxtMsg1.setPicUrl("pic url1");
    imgTxtMsg1.setUrl("url1");

    ImgTxtMsg imgTxtMsg2 = new ImgTxtMsg();
    imgTxtMsg1.setTitle("标题2");
    imgTxtMsg1.setDescription("描述2");
    imgTxtMsg1.setPicUrl("pic url2");
    imgTxtMsg1.setUrl("url2");

    List<ImgTxtMsg> items = new ArrayList<ImgTxtMsg>();
    items.add(imgTxtMsg1);
    items.add(imgTxtMsg2);
    String beanToXML = XStreamUtils.beanToXML(items);
    System.out.println(beanToXML);
  }

  /**
   * 处理图文
   */
  @Test
  public void process() {
    // 实例化xStream
    XStream xStream = new XStream(new DomDriver());
    // 设置类别名
    xStream.alias("Articles", Articles.class);
    xStream.alias("item", ImgTxtMsg.class);
    
  }
}

@NoArgsConstructor
@AllArgsConstructor
@Data
@XStreamAlias("user")
class User {
  private String username;
  private String password;
}
