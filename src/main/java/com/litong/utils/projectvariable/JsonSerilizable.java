package com.litong.utils.projectvariable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonSerilizable {

  /* 将链表序列化为字符串存入json文件中 */
  public static String serilizableForList(Object objList, String OutfilePathName) throws IOException {
    String listString = JSON.toJSONString(objList, true);// (maps,CityEntity.class);
    FileUtil.writeFile(OutfilePathName, listString);
    return listString;
  }

  /* 将json文件中的内容读取出来，反序列化为链表 */
  public static <T> List<T> deserilizableForListFromFile(String InputfilePathName, Class<T> clazz) throws IOException {
    String listString2 = FileUtil.readFile(InputfilePathName);
    List<T> list2 = JSON.parseArray(listString2, clazz);
    return list2;
  }

  /* 将HashMap序列化为字符串存入json文件中 */
  public static String serilizableForMap(Object objMap, String OutfilePathName) throws IOException {
    String listString = JSON.toJSONString(objMap, true);// (maps,CityEntity.class);
    FileUtil.writeFile(OutfilePathName, listString);
    return listString;
  }

  /* 将json文件中的内容读取出来，反序列化为HashMap */
  public static <K, V> Map<K, V> deserilizableForMapFromFile(String jsonStringFilePath, Class<K> k, Class<V> v)
      throws IOException {
    String jsonString = FileUtil.readFile(jsonStringFilePath);
    TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>(k, v) {
    };
    Map<K, V> map = JSON.parseObject(jsonString, typeReference);
    return map;
  }
}