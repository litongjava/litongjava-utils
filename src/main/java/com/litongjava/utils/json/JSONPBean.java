package com.litongjava.utils.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by litong on 2019/1/7 0007.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JSONPBean<T> {
  private String callback;
  private T t;
}
