package com.litong.utils.poi;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

import com.litongjava.utils.poi.POIExcelExportUtil;

public class POIExcelExportUtilTest {

  @Test
  public void testExport() {
    String[][] data = new String[5][3];
    for (int i = 0; i < data.length; i++) {
      String[] row = { i + "", "name" + i, "addr" + i };
      data[i] = row;
    }
    for (int i = 0; i < data.length; i++) {
      System.out.println(i + ":" + Arrays.toString(data[i]));
    }
    String[] titile = { "编号", "姓名", "地址" };
    File dst = new File("D:\\my_file\\my_excel\\user1.xls");
    POIExcelExportUtil.export(titile, data, dst);
  }

  @Test
  public void testInvokeGet() {
    User user = new User();
    user.setName("李通");
    String key = "name";
    String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
    Method method = null;
    try {
      method = user.getClass().getDeclaredMethod(methodName);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    try {
      Object invoke = method.invoke(user);
      System.out.println(invoke);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
