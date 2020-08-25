package com.litong.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author litong
 * @date 2018��6��5��14:54:41
 * jackson util
 */
public class JacksonUtil {
  private static final ObjectMapper objMapper = new ObjectMapper();

  public static String toJson(Object obj) {
    try {
      return objMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
