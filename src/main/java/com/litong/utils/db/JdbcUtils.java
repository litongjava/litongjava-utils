package com.litong.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * jdbc工具类
 * @author litong
 * @date 2020年9月26日 上午10:29:14
 * @desc
 */
public class JdbcUtils {

  /**
   * 连接数据库,连接成功返回true,连接失败返回false
   * @param driverClass
   * @param url
   * @param user
   * @param pswd
   * @param validateSql
   * @return
   * @throws Exception 
   */
  public static boolean connect(String driverClass, String url, String user, String pswd, String validateSql) throws Exception {
    // 1.注册驱动
//    try {
//      Class.forName(driverClass);
//    } catch (ClassNotFoundException e1) {
//      e1.printStackTrace();
//      return false;
//    }
    Class.forName(driverClass);
//    Connection connection = null;
//    Statement statement = null;
//    ResultSet resultSet = null;
    // 2.连接数据库,执行sql
//    try {
//      connection = DriverManager.getConnection(url, user, pswd);
//      statement = connection.createStatement();
//      resultSet = statement.executeQuery(validateSql);
//    } catch (SQLException e) {
//      e.printStackTrace();
//      return false;
//    } finally {
//      IOUtils.closeQuietly(resultSet);
//      IOUtils.closeQuietly(statement);
//      IOUtils.closeQuietly(connection);
//    }
    // 3.升级版,据说自动回收资源
    try (Connection connection = DriverManager.getConnection(url, user, pswd);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(validateSql);) {
    }
    return true;
  }

  public static void main(String[] args) {
    String driverClass = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost/monitoring_db?characterEncoding=UTF8";
    String user = "root";
    String pswd = "";
    String validateSql = "select 1";
    try {
      boolean connect = connect(driverClass, url, user, pswd, validateSql);
      if (connect) {
        System.out.println("连接成功");
      } else {
        System.out.println("连接失败");
      }
    } catch (Exception e) {
      String name = e.getClass().getName();
      String message = e.getMessage();
      System.err.println("出现异常:" + name + ":" + message);
    }

  }

  /**
   * 获取数据库的类型
   * mysql jdbc:mysql://localhost/monitoring_db
   * oracle jdbc:oracle:thin:@192.168.28.220:1521/orcl
   * @param jdbcUrl
   * @return
   */
  public static String getJdbcType(String jdbcUrl) {
    if (jdbcUrl.startsWith("jdbc:mysql:")) {
      return DbTypeConstants.mysql;
    } else if (jdbcUrl.startsWith("jdbc:oracle")) {
      return DbTypeConstants.oracle;
    }
    return null;
  }

  /**
   * 获取驱动类名
   * @param jdbcType
   * @return
   */
  public static String getDriverClass(String jdbcType) {
    if(DbTypeConstants.mysql.contains(jdbcType)) {
      return DbTypeConstants.mysqlDriverClass;
    }else if(DbTypeConstants.oracle.contains(jdbcType)) {
      return DbTypeConstants.oracleDriverClass;
    }
    return null;
  }

  /**
   * 返回验证sql
   * @param jdbcType
   * @return
   */
  public static String getValidateSql(String jdbcType) {
    if(DbTypeConstants.mysql.contains(jdbcType)) {
      return DbTypeConstants.mysqlValidateSql;
    }else if(DbTypeConstants.oracle.contains(jdbcType)) {
      return DbTypeConstants.oracleValidateSql;
    }
    return null;
  }
}