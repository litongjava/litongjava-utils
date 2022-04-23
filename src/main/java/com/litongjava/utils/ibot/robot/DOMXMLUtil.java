package com.litongjava.utils.ibot.robot;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于JDK的DocumentBuilder封装的xml工具
 * 
 * @author litong
 * @date 2019年5月31日_下午12:06:08
 * @version 1.0
 * @desc
 */
public class DOMXMLUtil {
  private static DocumentBuilder builer = null;
  private static Transformer transformer = null;
  static {
    initDocumentBuilder();
    initRransformer();
  }

  private static void initDocumentBuilder() {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setIgnoringElementContentWhitespace(true);
    try {
      builer = documentBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }

  public static void initRransformer() {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    // 由工厂创建转换器实例：
    try {
      transformer = transformerFactory.newTransformer();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将解密后的xml转为map
   * 
   * @param fromXML
   * @return
   */
  public static Map<String, String> parseXmlToMap(String fromXML) {
    return parseDocumentToMap(parseXMLToDocument(fromXML));
  }

  /**
   * org.w3c.dom.Document To Map
   * 
   * @param document
   * @return
   */
  private static Map<String, String> parseDocumentToMap(Document document) {
    Map<String, String> map = new ConcurrentHashMap<>();
    Element rootElement = document.getDocumentElement();
    NodeList nodeList = rootElement.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      String nodeName = node.getNodeName();
      String nodeText = node.getTextContent();
      map.put(nodeName, nodeText);
    }
    return map;
  }

  public static Document parseXMLToDocument(String xml) {
    StringReader sr = null;
    try {
      sr = new StringReader(xml);
      return builer.parse(new InputSource(sr));
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      sr.close();
    }
    return null;
  }

  public static Document newDocument() {
    return builer.newDocument();
  }

  /**
   * @param tagName
   * @param content
   * @param document
   */
  public static void addToRoot(String tagName, String content, Document document) {
    Element rootElement = document.getDocumentElement();
    Element ele = document.createElement(tagName); // 创建ele
    rootElement.appendChild(ele); // 添加到rootEle
    ele.setTextContent(content); // 设置内容
  }

  /**
   * DOM 转为xml
   * 
   * @param document
   * @return
   */
  public static String domToXML(Document document) {
    document.setXmlStandalone(true);
    // 设置转换格式,设置输出到文档时的格式，编码,换行等：
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    // 由转换器把dom资源转换到结果输出流
    StringWriter stringWriter = new StringWriter();
    StreamResult streamResult = new StreamResult(stringWriter);
    DOMSource domSource = new DOMSource(document);
    try {
      transformer.transform(domSource, streamResult);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
    return stringWriter.toString();
  }
}