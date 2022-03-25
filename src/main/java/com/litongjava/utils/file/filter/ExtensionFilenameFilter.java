package com.litongjava.utils.file.filter;

import java.io.File;

import java.io.FilenameFilter;

public class ExtensionFilenameFilter implements FilenameFilter {

  private String extension;

  public ExtensionFilenameFilter(String extension) {
    this.extension = extension;
  }

  @Override
  public boolean accept(File dir, String name) {
    return name.endsWith(extension);
  }
}
