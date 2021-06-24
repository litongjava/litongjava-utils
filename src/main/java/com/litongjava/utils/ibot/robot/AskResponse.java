package com.litongjava.utils.ibot.robot;

import lombok.Data;

import java.util.List;

/**
 * @author litong
 * @date 2019年2月18日_下午5:29:37
 * @version 1.0
 */
@Data
public class AskResponse {

  private int type;
  private MessageType msgType;
  private List<String> args;
  private int msgId;
  private String content;
  private String nodeId;
  private double similarity;
  private String moduleId;
  private PropsBean props;
  private int callbackParamType;
  private List<String> relatedQuestions;
  private List<CommandsBean> commands;

  @Data
  public static class PropsBean {
    private String categoryId;
    private String objectId;
    private String sessionId;
    private String userId;
  }
}