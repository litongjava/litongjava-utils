package com.litong.utils.ibot.robot;

import lombok.Data;

import java.util.List;

/**
 * @author litong
 * @date 2019年7月5日_下午9:29:39
 * @version 1.0
 * @desc
 */
@Data
public class CommandsBean {
  private String name;
  private int state;
  private List<String> args;
}
