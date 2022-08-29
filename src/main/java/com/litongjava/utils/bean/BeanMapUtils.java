package com.litongjava.utils.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author bill robot
 * @date 2020年1月3日_下午12:36:17 
 * @version 1.0 
 * @desc
 */

public class BeanMapUtils {

  /**
   * Object转Map
   * @param obj object对象
   * @return
   */
  public static Map<String, Object> getObjectToMap(Object obj) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    Class<?> clazz = obj.getClass();
    System.out.println(clazz);
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      String fieldName = field.getName();
      Object value = null;
      try {
        value = field.get(obj);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      if (value == null) {
        value = "";
      }
      map.put(fieldName, value);
    }
    return map;
  }

//Map转Object
  public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
    if (map == null)
      return null;
    Object obj = beanClass.newInstance();
    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      int mod = field.getModifiers();
      if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
        continue;
      }
      field.setAccessible(true);
      if (map.containsKey(field.getName())) {
        field.set(obj, map.get(field.getName()));
      }
    }
    return obj;
  }
}
