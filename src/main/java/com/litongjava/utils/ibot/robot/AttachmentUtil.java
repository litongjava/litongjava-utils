package com.litongjava.utils.ibot.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttachmentUtil {
  /**
   * 判断返回的消息是不是附件消息 
   * 
   * @param content
   * @return
   */
  public static boolean isAttachment(String content) {
    String keys1 = "attachment.action?id=";
    String keys2 = "&name=";
    if (content.contains(keys1) && content.contains(keys2)) {
      return true;
    }
    return false;
  }

  /**
   * 返回附件的的下载地址
   */
  public static List<String> getAttachmengURL(String content) {
    String regex = "link url=\"(.*?)\"";
    List<String> attachmengURL = getAttachmengURL(content, regex);
    return attachmengURL;
  }

  /**
   * 返回附件的的下载地址
   */
  public static List<String> getAttachmengURL(String content, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(content);
    List<String> list = new ArrayList<>();
    while (matcher.find()) {
      String group = matcher.group(1);
      String[] split = group.split(",");
      for (String s : split) {
        list.add(s);
      }
    }
    return list;
  }

  /**
   * 一般情况下,content内容如下
   *
   *
   * 相关问
   *
   * 知识点满意度调查
   */
  // 将link中的这里设置为 <font size="18">这里</font>
  public static String addFontTag(String content, String fontSize) {
    /*
     * [link
     * url=\"http://ibotcluster.online.uairobot.com/robot/app/bjhg/attachment
     * .action?id=20180925125245390&name=LAI.docx\"]这里[/link]
     */
    Map<String, String> map = new HashMap<>();
    Pattern pattern = Pattern.compile("\\[link( url=\")?(.*?)(\")?\\](.*?)\\[/link\\]");
    Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
      String word = matcher.group(4);
      String oldString = matcher.group(0);
      String newString = oldString.replace(word, "<font size='" + fontSize + "'>" + word + "</font>");
      map.put(oldString, newString);
    }
    Set<Map.Entry<String, String>> entries = map.entrySet();
    for (Map.Entry<String, String> entry : entries) {
      content = content.replace(entry.getKey(), entry.getValue());
    }
    return content;
  }
}
