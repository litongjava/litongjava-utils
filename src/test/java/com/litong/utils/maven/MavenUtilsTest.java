package com.litong.utils.maven;

import org.junit.Test;

import com.litongjava.utils.maven.MavenUtils;

/**
 * @author litong
 * @date 2021年3月7日_下午3:58:00 
 * @version 1.0 
 * @desc
 */
public class MavenUtilsTest {

  @Test
  public void test() {
    String jarNameString = "spring-context-support-1.0.6.jar";
    String artifactId = MavenUtils.getArtifactId(jarNameString);
    System.out.println(artifactId);
  }
  
  @Test
  public void getVersion() {
    String jarName="spring-context-support-1.0.6.jar";
    String version = MavenUtils.getVersion(jarName);
    System.out.println(version);
  }

}
