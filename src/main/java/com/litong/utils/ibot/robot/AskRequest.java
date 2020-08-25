package com.litong.utils.ibot.robot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AskRequest {
  private String userId;
  private String sessionId;
  private String question;
  private String platform;
  private String location;
  private String brand;
  private String format;
  private String userIp;
}
