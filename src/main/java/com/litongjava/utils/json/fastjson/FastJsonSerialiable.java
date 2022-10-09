package com.litongjava.utils.json.fastjson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.litongjava.utils.projectvariable.FileUtil;

public class FastJsonSerialiable {

  /**
   * 将链表序列化为字符串存入json文件中
   * @param objList
   * @param OutfilePathName
   * @return
   * @throws IOException
   */
  public static String serilizableForList(Object objList, String OutfilePathName) throws IOException {
    String listString = JSON.toJSONString(objList);// (maps,CityEntity.class);
    FileUtil.writeFile(OutfilePathName, listString);
    return listString;
  }

  /**
   * 将json文件中的内容读取出来，反序列化为链表
   * @param InputfilePathName
   * @param clazz
   * @return
   * @throws IOException
   */
  public static <T> List<T> deserilizableForListFromFile(String InputfilePathName, Class<T> clazz) throws IOException {
    String listString2 = FileUtil.readFile(InputfilePathName);
    List<T> list2 = JSON.parseArray(listString2, clazz);
    return list2;
  }

  /**
   * 将HashMap序列化为字符串存入json文件中
   * @param objMap
   * @param OutfilePathName
   * @return
   * @throws IOException
   */
  public static String serilizableForMap(Object objMap, String OutfilePathName) throws IOException {
    String listString = JSON.toJSONString(objMap);// (maps,CityEntity.class);
    FileUtil.writeFile(OutfilePathName, listString);
    return listString;
  }

  /**
   * 将json文件中的内容读取出来，反序列化为HashMap
   * @param jsonStringFilePath
   * @param k
   * @param v
   * @return
   * @throws IOException
   */
  public static <K, V> Map<K, V> deserilizableForMapFromFile(String jsonStringFilePath, Class<K> k, Class<V> v) throws IOException {
    String jsonString = FileUtil.readFile(jsonStringFilePath);
    TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>(k, v) {
    };
    Map<K, V> map = JSON.parseObject(jsonString, typeReference);
    return map;
  }
}