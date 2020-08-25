package com.litong.utils.table;

import java.io.IOException;

import org.junit.Test;

import lombok.Data;

/**
 * @author bill robot
 * @date 2020年6月12日_下午5:58:38 
 * @version 1.0 
 * @desc
 */
public class TableUtilsTest {

  @Test
  public void test() {
    try {
      TableUtils.createTable(Aricle.class, "t_aricle");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

@Data
class Aricle {
  private int id;
  private String content;
}
