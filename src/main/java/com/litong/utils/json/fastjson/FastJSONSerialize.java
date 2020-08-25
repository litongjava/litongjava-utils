package com.litong.utils.json.fastjson;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class FastJSONSerialize {
  /* 序列化list */
  public static void serilizableForList(Object objList, File destination){
    String string = JSON.toJSONString(objList, true);// (maps,CityEntity.class);
    FileUtil.writeFile(string, destination);
  }

  /* 反序列化List */
  public static <T> List<T> deserilizableForListFromFile(File source, Class<T> clazz) {
    String string = FileUtil.readFile(source);
    List<T> list = JSON.parseArray(string, clazz);
    return list;
  }

  /* 序列化map */
  public static void serilizableForMap(Object objMap, File destination) {
    String string = JSON.toJSONString(objMap, true);
    FileUtil.writeFile(string, destination);
  }

  /* 反序列化map */
  public static <K, V> Map<K, V> deserilizableForMapFromFile(File source, Class<K> k, Class<V> v) {
    String string = FileUtil.readFile(source);
    TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>(k, v) {};
    Map<K, V> map = JSON.parseObject(string, typeReference);
    return map;
  }
}