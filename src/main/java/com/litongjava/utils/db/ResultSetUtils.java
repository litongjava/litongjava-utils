package com.litongjava.utils.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author create by ping-e-lee on 2021年4月6日 上午12:03:38 
 * @version 1.0 
 * @desc
 */
public class ResultSetUtils {
  public static List<Map<Object, Object>> toListMap(ResultSet resultSet) {
    List<Map<Object, Object>> list = new ArrayList<>();
    try {
      // 获得结果集结构信息（元数据）
      ResultSetMetaData md = resultSet.getMetaData();
      // ResultSet列数
      int columnCount = md.getColumnCount();
      // ResultSet转List<Map>数据结构
      // next用于移动到ResultSet的下一行，使下一行成为当前行
      while (resultSet.next()) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {// 遍历获取对当前行的每一列的键值对，put到map中
          // rs.getObject(i) 获得当前行某一列字段的值
          map.put(md.getColumnName(i).toLowerCase(), resultSet.getObject(i));
        }
        list.add(map);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}
