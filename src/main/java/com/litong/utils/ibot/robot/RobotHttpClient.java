package com.litong.utils.ibot.robot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.litong.utils.http.commonshttpclient.HttpClientUtil;
import com.litong.utils.string.StringUtils;
import com.litong.utils.url.URLUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2019年2月18日_下午4:54:54
 * @version 1.0
 * @see 08.robot-http接口-v1.1.docx
 */
@Slf4j
public class RobotHttpClient {

  /**
   * 请求知识库
   */
  public static AskResponse ask(String robotURL, AskRequest data) {
    StringBuffer append = URLUtil.append(robotURL, "ask.action");
    Map<String, String> params = new HashMap<String, String>();
    params.put("userId", data.getUserId());
    params.put("question", data.getQuestion());
    params.put("sessionId", data.getSessionId());
    params.put("format", data.getFormat());
    params.put("platform", data.getPlatform());
    params.put("location", data.getLocation());
    params.put("brand", data.getBrand());
    String httpResponse = ask(append.toString(), params);
    log.info(httpResponse);
    String format = data.getFormat();
    if (format != null && format.equalsIgnoreCase("json")) {
      AskResponse ap = JSON.parseObject(httpResponse, AskResponse.class);
      // 添加userId和sessionId
      if (StringUtils.isEmpty(ap.getProps())) {
        AskResponse.PropsBean props = new AskResponse.PropsBean();
        props.setUserId(data.getUserId());
        props.setSessionId(data.getSessionId());
        ap.setProps(props);
      }
      return ap;
    }
    // 处理xml格式
    AskResponse ap = AskResponseUtil.xmlToAskResponse(httpResponse);
    return ap;
  }

  public static String ask(String url, Map<String, String> params) {
    log.info("url:" + url + "," + "params:" + params);
    String httpResponse = HttpClientUtil.post(url, null, params);
    return httpResponse;
  }

  public static List<String> getHotQuestion(String robotURL, HotRequest data) {
    if (StringUtils.isEmpty(data.getFormat())) {
      data.setFormat("json");
    }
    String url = URLUtil.append(robotURL, "p4pages/hot-question.action").toString();
    Map<String, String> params = new HashMap<>();
    params.put("platform", data.getPlatform());
    params.put("brand", data.getBrand());
    params.put("maxReturn", data.getMaxReturn() + "");
    params.put("type", data.getType() + "");
    params.put("format", data.getFormat());
    String httpResponse = HttpClientUtil.post(url.toString(), null, params);
    List<String> retval = JSON.parseArray(httpResponse, String.class);
    return retval;
  }

}
