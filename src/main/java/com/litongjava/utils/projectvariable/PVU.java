package com.litongjava.utils.projectvariable;



import java.util.Map;

public class PVU {
  public static void add(String key, String commnet, String value, boolean isReadOnly, boolean isSave) {
    ProjectVariableUtil.add(key, commnet, value, isReadOnly, isSave);
  }

  public static void setValue(ProjectVariable variable, boolean isSave) {
    ProjectVariableUtil.setValue(variable, isSave);
  }

  public static String getValue(String key) {
    return ProjectVariableUtil.getValue(key);
  }
  

  public static int size() {
    return ProjectVariableUtil.size();
  }

  public static Map<String, ProjectVariable> getParameters() {
    return ProjectVariableUtil.getParameters();
  }

  public static boolean getBooleanValue(String key) {
    return getValue(key).equals("true") ? true : false;
  }

  public static boolean isDev() {
    return ProjectVariableUtil.isDev();
  }
  
  public static void init(ProjectVariableInit pvi) {
    ProjectVariableUtil.init(pvi); 
  }
}
