package com.litong.utils.maven;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Administrator
 * @date 2019年1月5日_上午9:20:09
 * @version 1.0
 */
public class MavenUtilTest {

  @Test
  public void test() {
    fail("Not yet implemented");
  }

  /**
   * 如果检测到损坏的jar包则删除
   */
  @Test
  public void deleteBadJarInMavenLoacalRepository() {
    MavenUtil mavenUtil = new MavenUtil();
    String dirname = "D:\\dev_mavenRepository";
    mavenUtil.deleteBadJarInMavenLoacalRepository(dirname);
  }

  /**
   * 测试安装
   */
  @Test
  public void testInstall() {
    //String mvnPath = "\"D:\\Program Files (x86)\\JetBrains\\IntelliJ IDEA 2016.1\\plugins\\maven\\lib\\maven2\\bin\\mvn.bat\"";
    String mvnPath="mvn";
    String jarPath = "D:\\dev_workspace\\java\\hg_project\\ibot-parent\\ibot-robot-9.0-20190820-alpha\\lib";
    String localMavenPath = "D:\\dev_mavenRepository";
    String install = MavenUtil.install(mvnPath,jarPath, localMavenPath);
    System.out.println(install);
  }

}
