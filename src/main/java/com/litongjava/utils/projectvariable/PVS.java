package com.litongjava.utils.projectvariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PVS {

  public static JSONObject list(int page, int row) {
    JSONObject retval = new JSONObject();
    retval.put("total", PVU.size());
    Map<String, ProjectVariable> map = PVU.getParameters();
    Collection<ProjectVariable> values = map.values();
    List<ProjectVariable> list = new ArrayList<>(values);

    int start = (page - 1) * row;
    int end = start + row;
    if (end > list.size()) {
      end = list.size();
    }
    JSONArray jsonArray = new JSONArray();
    for (; start < end; start++) {
      JSONObject j = new JSONObject();
      j.put("key", list.get(start).getKey());
      j.put("value", list.get(start).getValue());
      j.put("comment", list.get(start).getComment());
      j.put("isReadOnly", list.get(start).isReadOnly());
      jsonArray.add(j);
    }
    retval.put("rows", jsonArray);
    return retval;
  }
}
