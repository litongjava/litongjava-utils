package com.litongjava.utils.excel;

import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Ping E Lee
 *
 */
public class PoiExcelUtilsTest {

  @Test
  public void test() {
    String filepath = "D:\\document\\secret\\appkey-and-appscret.xls";
    List<Map<String, Object>> listMap = PoiExcelUtils.readExcel(filepath, 0);
    System.out.println(listMap.size());
    for (Map<String, Object> map : listMap) {
      System.out.println(map);
    }

  }

}
