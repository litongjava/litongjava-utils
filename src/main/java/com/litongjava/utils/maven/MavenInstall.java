package com.litongjava.utils.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.litongjava.utils.xml.dom.DOMUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MavenInstall {
  public static List<String> notSupportedJar = new ArrayList<String>();

  public static String install(String mvnPath, String jarPath, String localMavenPath) {
    String retval = "<dependencies>";
    File jarPathFile = new File(jarPath);
    File[] listFiles = jarPathFile.listFiles();
    for (File file : listFiles) {
      if (file.getName().endsWith(".jar")) {
        String dependency = install(mvnPath, file, localMavenPath);
        if (dependency != null) {
          retval += dependency;
        }
      }
    }
    retval += "</dependencies>";
    System.out.println("需要手工处理的jar");
    for (String s : notSupportedJar) {
      System.out.println(s);
    }
    return retval;
  }

  /**
   * 失败返回null
   */
  private static String install(String mvnPath, File file, String localMavenPath) {
    Dependency dependency = null;
    ZipFile zipFile = null;
    try {
      zipFile = new ZipFile(file);
      dependency = getDependency(zipFile);
    } catch (ZipException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      close(zipFile);
    }
    if (dependency != null) {
      // 安装到本地,仅仅生成安装命令
      String cmd = executeInstallCommand(mvnPath, dependency, localMavenPath);
      System.out.println(cmd);
      String retval = dependencyToXML(dependency);
      return retval;
    }
    return null;

  }

  /**
   * 获取 安装命令
   */
  private static String executeInstallCommand(String mvmpath, Dependency dependency, String localRepositoryPath) {
    // @see org.apache.maven.plugin.install.InstallFileMojo
    StringBuffer strBuf = new StringBuffer();
    strBuf.append(mvmpath + " install:install-file");
    strBuf.append(" -DgroupId=" + dependency.getGroupId());
    strBuf.append(" -DartifactId=" + dependency.getArtifactId());
    strBuf.append(" -Dversion=" + dependency.getVersion());
    strBuf.append(" -Dfile=" + dependency.getJarPath());
    strBuf.append(" -DpomFile=" + dependency.getPomPath());
    strBuf.append(" -DlocalRepositoryPath=" + localRepositoryPath);
    strBuf.append(" -DcreateChecksum=" + "true");
    strBuf.append(" -Dpackaging=jar");
    String retval = strBuf.toString();
    // String exec=null;
    // try {
    // exec = RuntimeUtil.exec(retval);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // System.out.println(exec);
    return retval;
  }

  /**
   * 从zipFile中获取pom.xml内容,是失败返回null
   * 
   * @param zipFile
   * @return
   */
  private static Dependency getDependency(ZipFile zipFile) {
    String jarPath = zipFile.getName(); // 返回的就是全路径
    log.info("处理jar:" + jarPath);
    Dependency dependency = new Dependency();
    dependency.setJarPath(zipFile.getName());
    String pomPath = jarPath + ".pom";
    String[] array = getPom(zipFile, pomPath);
    if (array[0].equals("0")) {
      Document document = DOMUtils.stringToDom(array[2]);
      Element rootEle = document.getDocumentElement();
      NodeList nodeList = rootEle.getElementsByTagName("groupId");
      String groupId = null;
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node item = nodeList.item(i);
        if (item.getParentNode().getNodeName().equals("project")) {
          groupId = item.getTextContent();
          break;
        }
      }
      if (groupId == null) {
        groupId = nodeList.item(0).getTextContent();
      }
      dependency.setGroupId(groupId);

      nodeList = rootEle.getElementsByTagName("artifactId");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node item = nodeList.item(i);
        if (item.getParentNode().getNodeName().equals("project")) {
          String artifactId = item.getTextContent();
          dependency.setArtifactId(artifactId);
          break;
        }
      }

      nodeList = rootEle.getElementsByTagName("version");
      String version = null;
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node item = nodeList.item(i);
        if (item.getParentNode().getNodeName().equals("project")) {
          version=item.getTextContent();
          break;
        }
      }
      if (version == null) {
        version = nodeList.item(0).getTextContent();
      }
      dependency.setVersion(version);
    } else {
      notSupportedJar.add(jarPath);
      return null;
    }
    dependency.setPomPath(pomPath);
    return dependency;
  }

  /**
   * @return 包含3个元素[状态值,pomPath,pomXml内容],如果状态值=="0",说明成功,否则说明失败
   * 
   */
  private static String[] getPom(ZipFile zipFile, String pomPath) {
    Enumeration<? extends ZipEntry> entries = zipFile.entries();
    while (entries.hasMoreElements()) {
      ZipEntry zipEntry = entries.nextElement();
      if (zipEntry.getName().endsWith("pom.xml")) {
        String text = getWriteFile(zipFile, zipEntry, pomPath);
        String[] retval = { "0", pomPath, text };
        return retval;
      }
    }
    String[] retval = { "1", pomPath, "" };
    return retval;
  }

  /**
   * 将 zipEntry的内容读取为文本,写入文件,并返回
   * 
   * @return
   */
  @SuppressWarnings("deprecation")
  private static String getWriteFile(ZipFile zipFile, ZipEntry zipEntry, String pomPath) {
    InputStream inputStream = null;
    String text = null;
    try {
      inputStream = zipFile.getInputStream(zipEntry);
      text = getText(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
    try {
      FileUtils.writeStringToFile(new File(pomPath), text);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return text;
  }

  /**
   * 读取文件中的内容
   */
  private static String getText(InputStream inputStream) {
    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
    StringBuffer stringBuffer = new StringBuffer();
    String line = null;
    try {
      while ((line = bufReader.readLine()) != null) {
        stringBuffer.append(line);
        stringBuffer.append(System.getProperty("line.separator"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(bufReader);
    }
    return stringBuffer.toString();
  }

  private static void close(ZipFile zipFile) {
    if (zipFile != null)
      try {
        zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  private static String dependencyToXML(Dependency dependency) {
    StringBuffer strBuf = new StringBuffer();
    strBuf.append("<dependency>");
    strBuf.append("<groupId>" + dependency.getGroupId() + "</groupId>");
    strBuf.append("<artifactId>" + dependency.getArtifactId() + "</artifactId>");
    strBuf.append("<version>" + dependency.getVersion() + "</version>");
    strBuf.append("</dependency>");
    return strBuf.toString();
  }
}