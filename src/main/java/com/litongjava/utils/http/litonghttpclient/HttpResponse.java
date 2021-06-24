package com.litongjava.utils.http.litonghttpclient;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author create by ping-e-lee on 2021年3月19日 下午11:56:47 
 * @version 1.0 
 * @desc
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpResponse {
  private int code;
  private Map<String, List<String>> header;
  private String response;

}
