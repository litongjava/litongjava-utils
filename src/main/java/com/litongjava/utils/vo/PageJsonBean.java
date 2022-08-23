package com.litongjava.utils.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PageJsonBean<T> extends JsonBean<List<T>> {

  private long count;
  private long pageNo;
  private long pageSize;
 
  public PageJsonBean(long count, Integer pageNo, Integer pageSize, List<T> data) {
    this.count=count;
    this.pageNo=pageNo;
    this.pageSize=pageSize;
    super.setData(data);
  }
}