package com.litongjava.utils.ibot.robot;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.litongjava.utils.ibot.robot.AskResponse.PropsBean;


/**
 * @author litong
 * @date 2019年7月4日_下午12:53:13
 * @version 1.0
 * @desc
 */
public class AskResponseUtil {
  /**
   * xml转为askResponse
   * 
   * @param httpResponse
   * @return
   */
  public static AskResponse xmlToAskResponse(String httpResponse) {
    // log.info("httpResponse:" + httpResponse);
    Document document = DOMXMLUtil.parseXMLToDocument(httpResponse);
    Element rootElement = document.getDocumentElement();
    AskResponse ar = new AskResponse();
    ar.setType(getInt("Type", rootElement));
    ar.setContent(getString("Content", rootElement));
    ar.setNodeId(getString("NodeId", rootElement));
    ar.setModuleId(getString("ModuleId", rootElement));
    ar.setSimilarity(getDouble("Similarity", rootElement));
    ar.setCommands(getCommands("Commands", rootElement, CommandsBean.class));
    ar.setProps(getProps("Props", rootElement, AskResponse.PropsBean.class));
    ar.setRelatedQuestions(getRelatedQuestions("RelatedQuestions", rootElement));
    return ar;
  }

  private static List<String> getRelatedQuestions(String tagName, Element rootElement) {
    Node item = rootElement.getElementsByTagName(tagName).item(0);
    List<String> rq = new ArrayList<>();
    if (item instanceof Element) {
      Element ele = (Element) item;
      NodeList questionList = ele.getElementsByTagName("Question");
      for (int i = 0; i < questionList.getLength(); i++) {
        rq.add(questionList.item(i).getTextContent());
      }
    }
    return rq;
  }

  private static PropsBean getProps(String tagName, Element rootElement, Class<com.litongjava.utils.ibot.robot.AskResponse.PropsBean> clazz) {
    Node item = rootElement.getElementsByTagName(tagName).item(0);
    PropsBean propsBean = new PropsBean();
    if (item instanceof Element) {
      Element ele = (Element) item;
      NodeList propList = ele.getElementsByTagName("Prop");
      for (int i = 0; i < propList.getLength(); i++) {
        if (propList.item(i) instanceof Element) {
          ele = (Element) propList.item(i);
          String key = ele.getAttribute("key");
          switch (key) {
          case "categoryId":
            propsBean.setCategoryId(ele.getTextContent());
            break;
          case "objectId":
            propsBean.setObjectId(ele.getTextContent());
            break;
          case "sessionId":
            propsBean.setSessionId(ele.getTextContent());
            break;
          }
        }
      }
    }
    return propsBean;
  }

  private static List<CommandsBean> getCommands(String ragName, Element rootElement, Class<CommandsBean> clazz) {
    Node item = rootElement.getElementsByTagName(ragName).item(0);
    List<CommandsBean> retval = new ArrayList<>();

    if (item instanceof Element) {
      Element ele = (Element) item;
      NodeList commandList = ele.getElementsByTagName("Command");
      for (int i = 0; i < commandList.getLength(); i++) {
        CommandsBean commandsBean = new CommandsBean();
        if (commandList.item(i) instanceof Element) {
          ele = (Element) commandList.item(i);
          commandsBean.setName(ele.getAttribute("name"));
          commandsBean.setState(Integer.parseInt(ele.getAttribute("state")));

          NodeList argList = ele.getElementsByTagName("Arg");
          List<String> args = new ArrayList<>(argList.getLength());
          for (int j = 0; j < argList.getLength(); j++) {
            String content = argList.item(j).getTextContent();
            args.add(content);
          }
          commandsBean.setArgs(args);
        }
        retval.add(commandsBean);
      }

    }

    return retval;
  }

  private static Double getDouble(String string, Element rootElement) {
    String content = getString(string, rootElement);
    return Double.parseDouble(content);
  }

  private static Integer getInt(String string, Element rootElement) {
    String content = getString(string, rootElement);
    return Integer.parseInt(content);
  }

  private static String getString(String string, Element rootElement) {
    Node item = rootElement.getElementsByTagName(string).item(0);
    if (item == null) {
      return "0";
    } else {
      return item.getTextContent();
    }
  }

  /**
   * 转为XML String
   *
   * @param ar
   * @return
   */
  public static String askResponseToXML(AskResponse ar) {
    Document document = DOMXMLUtil.newDocument();
    Element rootElement = document.createElement("Response");
    document.appendChild(rootElement);

    DOMXMLUtil.addToRoot("Type", ar.getType() + "", document);
    DOMXMLUtil.addToRoot("Content", ar.getContent(), document);
    DOMXMLUtil.addToRoot("NodeId", ar.getNodeId(), document);
    DOMXMLUtil.addToRoot("ModuleId", ar.getModuleId(), document);
    DOMXMLUtil.addToRoot("Similarity", ar.getSimilarity() + "", document);
    if (ar.getCommands() != null) {
      addCommands("Commands", ar.getCommands(), document);
    }
    addProps("Props", ar.getProps(), document);
    addRq("RelatedQuestions", ar.getRelatedQuestions(), document);
    return DOMXMLUtil.domToXML(document);
  }

  /**
   * 添加相关问
   * 
   * @param string
   * @param relatedQuestions
   * @param document
   */
  private static void addRq(String tagName, List<String> relatedQuestions, Document document) {
    Element rootEle = document.getDocumentElement();
    Element ele = document.createElement(tagName);
    rootEle.appendChild(ele);
    if (relatedQuestions != null) {
      for (String r : relatedQuestions) {
        Element q = document.createElement("Question");
        ele.appendChild(q);
        q.setTextContent(r);
      }
    }
  }

  private static void addProps(String tagName, PropsBean props, Document document) {
    Element rootEle = document.getDocumentElement();
    Element ele = document.createElement(tagName);
    rootEle.appendChild(ele);

    Element categoryId = document.createElement("Prop");
    ele.appendChild(categoryId);
    categoryId.setAttribute("key", "categoryId");
    categoryId.setTextContent(props.getCategoryId());

    Element objectId = document.createElement("Prop");
    ele.appendChild(objectId);
    objectId.setAttribute("key", "objectId");
    objectId.setTextContent(props.getObjectId());

    Element sessionId = document.createElement("Prop");
    ele.appendChild(sessionId);
    sessionId.setAttribute("key", "sessionId");
    sessionId.setTextContent(props.getSessionId());
  }

  private static void addCommands(String tagName, List<CommandsBean> commands, Document document) {
    Element rootEle = document.getDocumentElement();
    Element ele = document.createElement(tagName);
    rootEle.appendChild(ele);

    for (CommandsBean c : commands) {
      Element subEle = document.createElement("Command");
      ele.appendChild(subEle);
      subEle.setAttribute("name", c.getName());
      subEle.setAttribute("state", c.getState() + "");
      List<String> args = c.getArgs();
      if (args != null) {
        for (String s : args) {
          Element arg = document.createElement("Arg");
          subEle.appendChild(arg);
          arg.setTextContent(s);
        }
      }
    }
  }
 
}
