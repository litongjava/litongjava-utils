package com.litong.utils.maven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litong on 2018/9/26 0026. jar包依赖 bean
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency {
  private String gruopId, artifactId, version, jarPath, pomPath;
}
