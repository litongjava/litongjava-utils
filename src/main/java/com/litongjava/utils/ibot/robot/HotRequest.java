package com.litongjava.utils.ibot.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotRequest {
  private String platform;
  private String brand;
  private int maxReturn;
  private int type;
  private String format;
}