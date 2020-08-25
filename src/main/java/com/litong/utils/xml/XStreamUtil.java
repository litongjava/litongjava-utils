package com.litong.utils.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author litong
 * @date 2018年12月4日_下午8:03:29 
 * @version 1.0
 * xml转bean工具类 
 */
public class XStreamUtil {
  private static XStream xStream = null;
  static {
    // 创建解析XML对象
    xStream = new XStream(new DomDriver());
    // 设置默认安装防护
    XStream.setupDefaultSecurity(xStream);
  }

  /**
   * bean to xml
   * @param <T>
   */
  public static <T> String beanToXML(Object obj) {
    // 声明XStream注解来源
    xStream.processAnnotations(obj.getClass());
    String xml = xStream.toXML(obj);
    return xml;
  }

  /**
   * xml to bean
   */
  public static <T> T XMLToBean(String xml, Class<T> clazz) {
    // 处理注解
    xStream.processAnnotations(clazz);
    // 设置类的别名
    xStream.allowTypes(new Class[] { clazz });
    @SuppressWarnings("unchecked")
    // 将XML字符串转为bean对象
    T t = (T) xStream.fromXML(xml);
    return t;
  }
}
