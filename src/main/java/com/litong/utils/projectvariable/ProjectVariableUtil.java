package com.litong.utils.projectvariable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProjectVariableUtil {
  private static Map<String, ProjectVariable> parameters = new TreeMap<>();
  private static String configFileName = null;
  public static String name; // 模式
  public static String mode; // 工程名

  static {
    name = NameAndMod.map.get(NameAndMod.name);
    if (isEmpty(name))
      name = "";
    mode = NameAndMod.map.get(NameAndMod.mode);
    if (isEmpty(mode))
      mode = "";
    configFileName = "project-variable-" + name + "-" + mode + ".json";
    File configFile = new File(configFileName);
    log.info("工程变量配置文件全路径:" + configFile.getAbsolutePath());

    // 判断文件是否存在
    boolean exists = configFile.exists();
    try {
      if (exists) {
        parameters = JsonSerilizable.deserilizableForMapFromFile(configFileName, String.class, ProjectVariable.class);
      }
    } catch (IOException e) {
      log.info("读取json配置文件失败");
      e.printStackTrace();
    }
    // 启动后如果值不存在,则设置默认值
    add("project.name", "工程名字", name, true, false);
    add("project.mode", "工程的开发模式", mode, true, false);
    ProjectVariableInit.initValue();
    // 保存配置文件
    ProjectVariableUtil.store();
  }

  public static boolean isEmpty(Object str) {
    return (str == null || "".equals(str));
  }

  /**
   * @return 如果是开发模式, 返回true
   */
  public static boolean isDev() {
    return parameters.get("project.mode").getValue().equals("dev");
  }

  /**
   * 获取参数值
   */
  public static String getValue(String key) {
    String value = parameters.get(key).getValue();
    return value;
  }

  /**
   * 添加或修改工程变量并保存到本地
   * @param isSave 如果为true,则保存到文件
   *          
   */
  public static void setValue(ProjectVariable variable, boolean isSave) {
    parameters.put(variable.getKey(), variable);
    if (isSave) {
      ProjectVariableUtil.store();
    }
  }

  /**
   * 添加或修改工程变量
   */
  public static void add(String key, String commnet, String value, boolean isReadOnly, boolean isSave) {
    ProjectVariable variable = parameters.get(key);
    if (isSave) {
      variable = new ProjectVariable();
      variable.setKey(key);
      variable.setComment(commnet);
      variable.setValue(value);
      variable.setReadOnly(isReadOnly);
      ProjectVariableUtil.setValue(variable, isSave); // 添加或修改工程变量并保存到本地
    } else {
      if (variable == null) {
        variable = new ProjectVariable();
        variable.setKey(key);
        variable.setComment(commnet);
        variable.setValue(value);
        variable.setReadOnly(isReadOnly);
        ProjectVariableUtil.setValue(variable, isSave); // 添加或修改工程变量并保存到本地
      }
    }
  }

  /**
   * 修改工程变量
   */
  public static String setValue(String key, String value) {
    ProjectVariable projectVariable = parameters.get(key);
    String retval = null;
    if (projectVariable != null) {
      projectVariable.setValue(value);
      retval = "successed";
    } else {
      projectVariable = new ProjectVariable();
      projectVariable.setKey(key);
      projectVariable.setValue(value);
      parameters.put(key, projectVariable);
      retval = "craeted";
    }
    ProjectVariableUtil.store();
    return retval;
  }

  /**
   * 获取所有值
   */
  public static Map<String, ProjectVariable> getParameters() {
    return parameters;
  }

  /**
   * 保存到本地磁盘
   */
  public static void store() {
    try {
      JsonSerilizable.serilizableForMap(parameters, configFileName);
    } catch (IOException e) {
      log.info("保存json配置文件失败");
      e.printStackTrace();
    }
  }

  public static int size() {
    return parameters.size();
  }
}