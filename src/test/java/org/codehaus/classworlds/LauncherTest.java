package org.codehaus.classworlds;

import org.junit.Test;

public class LauncherTest {

  @Test
  public void test() {
    // mvn install:install-file -DgroupId=incesoft.ai -DartifactId=ai-commonsapi
    // -Dversion=2.8
    // -Dfile=E:\package\ibot\20190521\robot\WEB-INF\lib\ai-commonsapi-2.8.jar
    // -DpomFile=E:\package\ibot\20190521\robot\WEB-INF\lib\ai-commonsapi-2.8.jar.pom
    // -DlocalRepositoryPath=E:\mavenLocalPath -DcreateChecksum=true -Dpackaging=jar
    String[] args = { "install:install-file",
        "-Dfile=E:\\package\\ibot\\20190521\\robot\\WEB-INF\\lib\\ai-commonsapi-2.8.jar",
        "-DpomFile=E:\\package\\ibot\\20190521\\robot\\WEB-INF\\lib\\ai-commonsapi-2.8.jar.pom",
        "-DlocalRepositoryPath=E:\\mavenLocalPath -DcreateChecksum=true -Dpackaging=jar" };
    Launcher.main(args);
  }
}
