package com.litongjava.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author litong
 * jackson utils
 */
public class JacksonUtils {
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
