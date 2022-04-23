package com.litongjava.utils.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.utils.string.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author bill robot
 * @date 2020年6月9日_上午2:16:58 
 * @version 1.0 
 * @desc
 */
@Slf4j
public class LReflectionUtils {
  /**
   * 如果属性中string的值是字符串,则转为null
   * @param <T>
   * @param e
   * @return
   */
  public static <T> T convertEmpytStringToNull(T e) {
    Field[] fields = e.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getGenericType().toString().equals("class java.lang.String")) {
        field.setAccessible(true);
        Object obj = null;
        try {
          obj = field.get(e);
        } catch (IllegalArgumentException e1) {
          e1.printStackTrace();
        } catch (IllegalAccessException e1) {
          e1.printStackTrace();
        }
        if ("".equals(obj)) {
          try {
            field.set(e, null);
          } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
          } catch (IllegalAccessException e1) {
            e1.printStackTrace();
          }
        }
      }
    }
    return e;
  }

  /**
   * 执行实体类的get方法
   * @param obj
   * @param fieldName
   * @return
   */
  public static Object invokeGetMethod(Object obj, String fieldName) {
    Class<? extends Object> clazz = obj.getClass();
    PropertyDescriptor pd = null;
    try {
      pd = new PropertyDescriptor(fieldName, clazz);
    } catch (IntrospectionException e) {
      log.error("执行new PropertyDescriptor(fieldName, clazz)出现错误");
      e.printStackTrace();
    }
    Method readMethod = pd.getReadMethod();
    if (readMethod != null) {
      Object invoke = null;
      try {
        invoke = readMethod.invoke(obj);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
      return invoke;
    } else {
      return null;
    }
  }

  public static Map<String, Object> convertObjectToMap(Object obj) {
    Map<String, Object> dstMap = new HashMap<>();
    Field[] fields = obj.getClass().getDeclaredFields();
    for (Field field : fields) {
      if ("serialVersionUID".equals(field.getName())) {
        continue;
      }

      String dstName = CamelUnderlineUtil.camelToUnderline(field.getName());
      Object dstObject = invokeGetMethod(obj, field.getName());
      if (StringUtils.isEmpty(dstObject)) {
        continue;
      } else if (dstObject instanceof Date) {
        dstObject = (Date) dstObject;
      } else if (dstObject instanceof ArrayList) {
        log.info("无法处理的的ArrayList {},{}", obj.getClass(), field.getName());
        dstObject = "";
      }
      dstMap.put(dstName, dstObject);
    }
    return dstMap;
  }

}
