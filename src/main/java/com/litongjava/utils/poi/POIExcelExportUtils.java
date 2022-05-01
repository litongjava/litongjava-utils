package com.litongjava.utils.poi;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIExcelExportUtils {
  public static void export(String[] title, String[][] data, File dst) {
    // 创建一个excel表格
    HSSFWorkbook wb = new HSSFWorkbook();
    // 创建一个工作簿
    HSSFSheet sheet = wb.createSheet();
    setTitle(sheet, title);
    setData(sheet, data);
    write(dst, wb);
  }

  private static void write(File dst, HSSFWorkbook wb) {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(dst);
      // 工作簿的中的内容写入到输入流中
      wb.write(out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      closeQuietly(out);
    }
  }

  /**
   * 设置标题
   */
  private static void setTitle(HSSFSheet sheet, String[] title) {
    // 创建标题行
    HSSFRow row = sheet.createRow(0);
    for (int i = 0; i < title.length; i++) {
      // 创建单元格
      HSSFCell cell = row.createCell(i);
      cell.setCellValue(title[i]);
    }
  }

  /**
   * 设置数据
   */
  private static void setData(HSSFSheet sheet, String[][] data) {
    for (int i = 0; i < data.length; i++) {
      HSSFRow row = sheet.createRow(i + 1);
      for (int j = 0; j < data[i].length; j++) {
        row.createCell(j).setCellValue(data[i][j]);
      }
    }
  }

  public static void closeQuietly(Closeable io) {
    if (io != null) {
      try {
        io.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static <T> void exportForList(LinkedHashMap<String, String> title, List<T> data, File dst) {
    // 创建一个excel表格
    HSSFWorkbook wb = new HSSFWorkbook();
    // 创建一个工作簿
    HSSFSheet sheet = wb.createSheet();
    setTitle(sheet, title);
    setData(sheet, title, data);
    write(dst, wb);
  }

  /**
   * 设置数据
   */
  private static <T> void setData(HSSFSheet sheet, LinkedHashMap<String, String> title, List<T> data) {
    for (int i = 0; i < data.size(); i++) {
      HSSFRow row = sheet.createRow(i + 1);
      T t = data.get(i);
      int j = 0;
      for (Map.Entry<String, String> e : title.entrySet()) {
        HSSFCell cell = row.createCell(j++);
        String key = e.getKey();
        Object value = getMethodValue(t, key);
        setCellValue(cell, value);
      }
    }
  }

  /**
   * 设置不同类型的cell的值
   */
  private static void setCellValue(HSSFCell cell, Object value) {
    if (value instanceof String) {
      cell.setCellValue((String) value);
    } else if (value instanceof Integer) {
      cell.setCellValue((Integer) value);
    }
  }

  /**
   * 执行get方法,获取返回值
   */
  private static <T> Object getMethodValue(T t, String key) {
    String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
    Method method = null;
    try {
      method = t.getClass().getDeclaredMethod(methodName);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    Object reval = null;
    try {
      reval = method.invoke(t);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return reval;
  }

  /**
   * 设置标题
   */
  private static void setTitle(HSSFSheet sheet, LinkedHashMap<String, String> title) {
    // 创建标题行
    HSSFRow row = sheet.createRow(0);
    int i = 0;
    for (Map.Entry<String, String> e : title.entrySet()) {
      row.createCell(i++).setCellValue(e.getValue());
    }
  }
}
