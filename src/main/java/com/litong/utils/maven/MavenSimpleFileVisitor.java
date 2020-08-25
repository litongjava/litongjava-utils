package com.litong.utils.maven;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import com.litong.utils.digest.SHA1Util;

public class MavenSimpleFileVisitor extends SimpleFileVisitor<Path> {
  // 遍历文件
  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    // 获取文件绝对路径
    String absolutePath = file.toFile().getAbsolutePath();

    Path sha1File = null;
    if (absolutePath.endsWith(("jar"))) {
      sha1File = Paths.get(file.toString() + ".sha1");
    } else {
      return FileVisitResult.CONTINUE;
    }
    // 如果文件不存在,则跳出,执行下次循环
    boolean exists = sha1File.toFile().exists();
    if (!exists) {
      System.out.println(sha1File + "文件不存在,无法校验");
      return FileVisitResult.CONTINUE;
    }
    List<String> readAllLines = Files.readAllLines(sha1File);
    StringBuffer stringBuffer = new StringBuffer();
    for (String s : readAllLines) {
      if (s.equals("")) {
        continue;
      } else {
        stringBuffer.append(s);
      }
    }
    String digest = SHA1Util.getSha1(file.toString()); // 如果文件不存在,返回的digest为null
    if (digest == null) {
      return FileVisitResult.CONTINUE;
    }
    if (digest.equals(stringBuffer.toString())) {
      // System.out.println(file + ":文件无损坏");
    } else {
      try {
        Files.delete(file);
        System.out.println(file + ":文件有损坏,已删除");
      } catch (Exception e) {
        System.out.println(file);
      }

    }
    return FileVisitResult.CONTINUE;
  }
}
