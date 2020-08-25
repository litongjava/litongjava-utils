package com.litong.utils.ibot.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.litong.utils.http.commonshttpclient.HttpClientUtil;
import com.litong.utils.ibot.robot.AskResponse.PropsBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author litong
 * @date 2019年7月5日_下午12:09:53
 * @version 1.0
 * @desc
 */
@Slf4j
public class NLPHttpclient {
  public static AskResponse ask(String nlpURL, AskRequest aq, String askEx) {
    AskResponse retval = new AskResponse();
    Map<String, String> params = new HashMap<String, String>();
    // params.put("ver", "-1");
    params.put("userId", aq.getUserId());
    params.put("question", aq.getQuestion());
    params.put("sessionId", aq.getSessionId());
    params.put("format", aq.getFormat());
    params.put("platform", aq.getPlatform());
    params.put("user_ask_ex", askEx);
    // params.put("from3rd", "true");
    log.info(nlpURL + "," + params);
    String httpResponse = HttpClientUtil.post(nlpURL, null, params);
    String format = aq.getFormat();
    if (format != null && format.equalsIgnoreCase("json")) {
      NLPResponse nr = JSON.parseObject(httpResponse, NLPResponse.class);
      if (nr.getType() == 0) { // 第三方服务返回的是 默认回复
        nr = getDefaultReply();
      }
      retval.setType(nr.getType());
      String content = nr.getContent();
      if (content.startsWith("\r\n")) {
        content = content.substring(2, nr.getContent().length());
        retval.setContent(content);
      }
      retval.setContent(content);
      retval.setSimilarity(nr.getSimilarity());
      retval.setRelatedQuestions(nr.getRelatedQuestions());
      retval.setModuleId(nr.getModuleId());
      List<CommandsBean> cs = nr.getCommands();
      PropsBean p = new AskResponse.PropsBean();
      //
      for (CommandsBean c : cs) {
        if (c.getName().equals("prop.categoryId")) {
          p.setCategoryId(c.getArgs().get(0));
          continue;
        } else if (c.getName().equals("prop.objectId")) {
          p.setObjectId(c.getArgs().get(0));
          continue;
        } else if (c.getName().equals("prop.sessionId")) {
          p.setSessionId(c.getArgs().get(0));
        } else {

        }
      }
      retval.setProps(p);
      retval.setCommands(cs);
    }
    return retval;
  }

  private static NLPResponse getDefaultReply() {
    // 构建默认回复
    NLPResponse nlpResponse = new NLPResponse();
    nlpResponse.setType(0);
    nlpResponse.setContent("defaultReply");
    nlpResponse.setCommands(new ArrayList<CommandsBean>());
    return nlpResponse;
  }
}
