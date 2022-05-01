package com.litongjava.utils.string;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author Ping E Lee
 *
 */
public class StringUtilsTest {

  @Test
  public void testEqualsStringString() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsEmptyString() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsBlank() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsEmptyObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testNotBlank() {
    fail("Not yet implemented");
  }

  @Test
  public void testRemoveSuffix() {
    fail("Not yet implemented");
  }

  @Test
  public void testRemovePrefix() {
    fail("Not yet implemented");
  }

  @Test
  public void testSplitPath() {
    fail("Not yet implemented");
  }

  @Test
  public void testIsNumeric() {
    fail("Not yet implemented");
  }

  @Test
  public void testNullTo0() {
    fail("Not yet implemented");
  }

  @Test
  public void testJoin() {
    String joinChar = "/";
    String year = "2022";
    String month = "12";
    String day = "31";
    String id = "001";
    String join = StringUtils.join(joinChar, year, month, day, id);
    System.out.println(join);
  }

}
