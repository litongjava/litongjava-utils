package com.litongjava.utils.db;

/**
 * @author litong
 * @date 2020年9月26日_下午4:13:08 
 * @version 1.0 
 * @desc
 */
public class DbTypeConstants {
  public static final String mysql = "mysql";
  public static final String oracle = "oracle";

  public static final String mysqlDriverClass = "com.mysql.jdbc.Driver";
  public static final String oracleDriverClass = "oracle.jdbc.driver.OracleDriver";

  public static final String mysqlValidateSql = "select 1";
  public static final String oracleValidateSql = "select 1 from dual";
}
