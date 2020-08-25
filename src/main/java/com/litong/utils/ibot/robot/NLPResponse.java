package com.litong.utils.ibot.robot;

import lombok.Data;

import java.util.List;

/**
 * @author litong
 * @date 2019年2月18日_下午5:29:37
 * @version 1.0
 */
@Data
public class NLPResponse {

  private int type;
  private String content;
  private String nodeId;
  private double similarity;
  private String moduleId;
  private int callbackParamType;
  private List<String> relatedQuestions;
  private List<CommandsBean> commands;

}