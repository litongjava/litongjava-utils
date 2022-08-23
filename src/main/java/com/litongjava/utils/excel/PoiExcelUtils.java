package com.litongjava.utils.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Poi的excel工具类
 */
public class PoiExcelUtils {

  /**
   * ExcelToMapUtils 非反射读取 map
   * @param filepath
   * @param index
   * @return
   */
  public static List<Map<String, Object>> readExcel(String filepath, int index) {
    List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    Workbook workbook = null;
    try {
      workbook = WorkbookFactory.create(new FileInputStream(filepath));
      Sheet sheet = workbook.getSheetAt(index);// 可根据index，sheetName等
      Row row = sheet.getRow(0); // 表头行
      List<String> keys = new ArrayList<String>();
      for (int i = 0; i < row.getLastCellNum(); i++) {
        Cell cell = row.getCell(i);
        keys.add(String.valueOf(getValue(cell))); // 取表头行的每一个元素，组装keys集合
      }
      // 从第二行开始循环行
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row currentRow = sheet.getRow(i);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        // 循环cell列
        for (int j = 0; j < currentRow.getLastCellNum(); j++) {
          map.put(keys.get(j), getValue(currentRow.getCell(j))); // 组装Map
        }
        mapList.add(map); // 组装List<Map>
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("excel解析出错");
    } finally {
      try {
        if (workbook != null) {
          workbook.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return mapList;
  }

  private static Object getValue(Cell cell) {
    if (cell == null) {
      return "";
    }
    if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
      return cell.getBooleanCellValue();
    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
      return cell.getNumericCellValue();
    } else {
      return String.valueOf(cell.getStringCellValue());
    }
  }
}