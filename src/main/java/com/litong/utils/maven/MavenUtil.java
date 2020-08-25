package com.litong.utils.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.litong.utils.string.StringUtil;

/**
 * Created by litong on 2018/9/26 0026. maven 工具类
 */
public class MavenUtil {

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
    String all = StringUtil.removePrefix(jarPath, repodir); // all =
                                                            // com\oracle\ojdbc14\10.2.0.4.0\ojdbc14-10.2.0.4.0.jar
    String[] splits = StringUtil.splitPath(all);
    String jarNmae = splits[splits.length - 1];
    // 获取 version and artifactid
    String version = getVersion(jarNmae);
    String artifactid = getArtifactid(jarNmae);
    // remote com.ojdbc14\10.2.0.4.0\ojdbc14-10.2.0.4.0.jar
    System.out.println(all);
    all = StringUtil.removeSuffix(all, "\\" + artifactid + "\\" + version + "\\" + jarNmae); // ==> all = com\ojdbc14
    String groupid = all.replace("\\", ".");// ==> all= com.ojdbc4
    System.out.println(all);
    // 构造返回值返回
    Dependency dependency = new Dependency();
    dependency.setArtifactId(artifactid);
    dependency.setVersion(version);
    dependency.setGruopId(groupid);
    return dependency;
  }

  /**
   * 根据 jar包 全名 获取 artifactid
   *
   * @param jar
   *          ojdbc14-10.2.0.4.0.jar
   * @return
   */
  public static String getArtifactid(String jar) {
    String[] strings = jar.split("-");
    return strings[0];
  }

  /**
   * 根据jar包全名获取 Version
   *
   * @param jar
   *          ojdbc14-10.2.0.4.0.jar
   * @return
   */
  public static String getVersion(String jar) {
    String[] strings = jar.split("-");
    String version = StringUtil.removeSuffix(strings[1], ".jar");
    return version;
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

  public static String install(String mvnPath,String jarPath, String localMavenPath) {
    return MavenInstall.install(mvnPath,jarPath, localMavenPath);

  }
}
