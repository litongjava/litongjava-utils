package com.litong.utils.xml;
/**
 * @author Administrator
 * @date 2019年1月23日_上午10:58:53 
 * @version 1.0 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XstreamCollectionStudy {

  /**
   * bean和集合
   */
  @Test
  public void preocessCollection() {
    // 实例化xStream
    XStream xStream = new XStream(new DomDriver());
    // 创建2个地址添加到list中
    Address address1 = new Address(001, "经三路", "450000");
    Address address2 = new Address(002, "朝阳区", "10000");
    List<Address> list = new ArrayList<Address>();
    list.add(address1);
    list.add(address2);
    // 实例化person
    Person person = new Person(001, 1, 26, "spark", list);
    // 设置类别名
    xStream.alias("person", Person.class);
    xStream.alias("address", Address.class);
    // 类成员别名
    xStream.aliasField("addressList", Person.class, "addList");
    xStream.aliasField("addressId", Address.class, "id");
    // 设置某个节点为父节点个属性
    xStream.useAttributeFor(Address.class, "zipcode");

    // xStream.aliasField("ZipCode", Address.class, "zipcode"); 与下面效果一样
    xStream.aliasAttribute(Address.class, "zipcode", "ZipCode");
    String xml = xStream.toXML(person);
    System.out.println(xml);

    // 将xml保存到文件
    File file = new File("d:/temp/List2XML.xml");
    if (!file.exists() || !file.isDirectory()) { // 创建目录
      String location = file.getAbsolutePath();
      String[] arry = location.split("\\\\");
      File file1 = new File(arry[0] + "/" + arry[1]);
      file1.mkdir();
    }
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.forName("utf-8"));
      BufferedWriter bufferWriter = new BufferedWriter(outputStreamWriter);
      xStream.toXML(person, bufferWriter);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void xmlCollectionToBean() {
    XStream xStream = new XStream(new DomDriver());
    File file = new File("d:/temp/List2XML.xml");
    FileInputStream fileInputStream = null;
    try {
      // 读取文件
      fileInputStream = new FileInputStream(file);
      // 设置别名
      xStream.alias("person", Person.class);
      xStream.alias("address", Address.class);
      xStream.aliasAttribute(Person.class, "addList", "addressList");
      xStream.aliasAttribute(Address.class, "id", "addressId");
      xStream.aliasAttribute(Address.class, "zipcode", "ZipCode"); // 前后顺序无关
      xStream.useAttributeFor(Address.class, "zipcode");
      // 转成实例类
      Person person = (Person) xStream.fromXML(fileInputStream);
      System.out.println(person);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
