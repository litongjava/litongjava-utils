package com.litongjava.utils.maven;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litong on 2018/9/26 0026. jar包依赖 bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("dependency")
public class Dependency {
  private String groupId, artifactId, version, jarPath, pomPath;
  
  public Dependency(String groupId, String artifactId, String version) {
    this.groupId=groupId;
    this.artifactId=artifactId;
    this.version=version;
  }
}
