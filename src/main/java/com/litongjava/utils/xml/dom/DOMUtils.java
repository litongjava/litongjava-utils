package com.litongjava.utils.xml.dom;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMUtils {
  private static DocumentBuilder db = null;
  static {
    // 创建factory
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    // 创建builder
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  public static Document stringToDom(String string) {
    StringReader stringReader = new StringReader(string);
    InputSource inputSource = new InputSource(stringReader);
    // 解析成document
    Document document = null;
    try {
      document = db.parse(inputSource);
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return document;
  }
}
