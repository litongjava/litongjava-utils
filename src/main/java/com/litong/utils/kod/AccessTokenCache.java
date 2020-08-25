package com.litong.utils.kod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author litong
 * @date 2019年2月17日_下午8:08:01
 * @version 1.0 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenCache {
  private String accessToken;
  private long expireTime;

  /**
   * 过期返回ture
   */
  public boolean isExpire() {
    return System.currentTimeMillis() > expireTime;
  }
}
