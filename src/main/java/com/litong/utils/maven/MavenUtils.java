package com.litong.utils.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.litong.utils.string.StringUtils;

/**
 * Created by litong on 2018/9/26 0026. maven 工具类
 */
public class MavenUtils {

  /**
   * 将 jar中的全路径转成成实体类
   * 
   * @param jars
   * @return
   */
  public static List<Dependency> parse(List<String> jars, String repodir) {
    List<Dependency> dependencies = new ArrayList<>();
    for (String jar : jars) {

      getDependency(jar, "D:\\dev_workspace\\java\\litong_prject\\maven-util\\target\\classes\\jar\\");
    }
    return dependencies;
  }

  public static Dependency getDependency(String jarAbsolutePath, String repodir) {
    // 去除 jar absolute path 中repodir部分
    File jarFile = new File(jarAbsolutePath);
    // File repodirFile = new File(repodir);
    String jarPath = jarFile.getAbsolutePath();
    // String repodirPath = repodirFile.getAbsolutePath();
    String all = StringUtils.removePrefix(jarPath, repodir); // all =
                                                             // com\oracle\ojdbc14\10.2.0.4.0\ojdbc14-10.2.0.4.0.jar
    String[] splits = StringUtils.splitPath(all);
    String jarNmae = splits[splits.length - 1];
    // 获取 version and artifactid
    String version = getVersion(jarNmae);
    String artifactid = getArtifactId(jarNmae);
    // remote com.ojdbc14\10.2.0.4.0\ojdbc14-10.2.0.4.0.jar
    System.out.println(all);
    all = StringUtils.removeSuffix(all, "\\" + artifactid + "\\" + version + "\\" + jarNmae); // ==> all = com\ojdbc14
    String groupid = all.replace("\\", ".");// ==> all= com.ojdbc4
    System.out.println(all);
    // 构造返回值返回
    Dependency dependency = new Dependency();
    dependency.setArtifactId(artifactid);
    dependency.setVersion(version);
    dependency.setGroupId(groupid);
    return dependency;
  }

  /**
   * 根据 jar包 全名 获取 artifactid
   *
   * @param jar
   *          ojdbc14-10.2.0.4.0.jar
   *          spring-context-support-1.0.6.jar
   * @return
   */
  public static String getArtifactId(String jarName) {
    String[] strings = jarName.split("-");
    return getArtifactId(strings);
  }

  /**
   * 
   * @param split
   */
  public static String getArtifactId(String[] strings) {
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < strings.length - 2; i++) {
      stringBuffer.append(strings[i]).append('-');
    }
    stringBuffer.append(strings[strings.length - 2]);
    return stringBuffer.toString();
  }

  /**
   * 根据jar包全名获取 Version
   *
   * @param jar
   *          ojdbc14-10.2.0.4.0.jar
   *          spring-context-support-1.0.6.jar
   * @return
   */
  public static String getVersion(String jar) {
    String[] strings = jar.split("-");
    return getVersion(strings);
  }

  public static String getVersion(String[] strings) {
    return StringUtils.removeSuffix(strings[strings.length - 1], ".jar");
  }

  /**
   * 删除maven本地库中损坏的jar包
   */
  public void deleteBadJarInMavenLoacalRepository(String dirname) {
    Path dir = Paths.get(dirname);
    try {
      Files.walkFileTree(dir, new MavenSimpleFileVisitor());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String install(String mvnPath, String jarPath, String localMavenPath) {
    return MavenInstall.install(mvnPath, jarPath, localMavenPath);
  }

  /**
   * 递归获取jar包和依赖描述
   * @param folderFile
   * @param listDependencies
   * @param localMavenPath
   * @return
   */
  public static List<Dependency> recursiveGetFile(File folderFile, List<Dependency> listDependencies, String localMavenPath) {
    File[] listFiles = folderFile.listFiles();
    for (File file : listFiles) {
      String filename = file.getName();
      // 如果是目录,继续列出目录下的文件
      if (file.isDirectory()) {
        recursiveGetFile(file, listDependencies, localMavenPath);
      } else if (filename.endsWith(".jar") && !filename.endsWith("sources.jar")) {
        listDependencies.add(getDependency(file, localMavenPath));
      }
    }
    return listDependencies;
  }

  /**
   * 从路径中获取依赖
   * @param file
   * @return
   */
  public static Dependency getDependency(File file, String localMavenPath) {
    Dependency e = new Dependency();
    String name = file.getName();
    String depencyString = file.getAbsolutePath().replace(localMavenPath, "").replace(name, "");
    String[] split = null;
    if (File.separatorChar == 92) { // widnwos os
      split = depencyString.split("\\\\");
    } else { // linux os or other os
      split = depencyString.split("/");
    }
    e.setVersion(split[split.length - 1]);
    e.setArtifactId(split[split.length - 2]);
    StringBuffer gruopId = new StringBuffer();
    for (int i = 1; i < split.length - 3; i++) {
      gruopId.append(split[i]).append(".");
    }
    gruopId.append(split[split.length - 3]);
    e.setGroupId(gruopId.toString());
    return e;
  }

}
