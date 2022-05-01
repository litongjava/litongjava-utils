package com.litong.utils.pinyin;

import java.util.Arrays;

import org.junit.Test;

import com.litongjava.utils.pinyin.PinyinUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @author Administrator
 * @date 2019年1月17日_下午4:51:57 
 * @version 1.0 
 */
public class PinYinUtilTest {

  @Test
  public void test() {
    String regex = "[0-9]";
    String string = "1";
    boolean isMatch = string.matches(regex);
    System.out.println(isMatch);
  }

  /**
   * 测试字符转转拼音
   */
  @Test
  public void toPinyin() {
    String input = "中华人民共和国";
    PinyinUtils pinyinUtils = new PinyinUtils();
    String pinyinString = pinyinUtils.toPinyinString(input);
    System.out.println(pinyinString);
  }

  /**
   * 判断是否为汉字
   */
  @Test
  public void isHanzi() {
    String regex = "[\\u4E00-\\u9FA5]+";
    System.out.println("内资".matches(regex));
    System.out.println("neizi".matches(regex));
    System.out.println("内zi".matches(regex));
  }

  /**
   * 判断是否为汉字
   */
  @Test
  public void isHanzi0() {
    PinyinUtils pinyinUtils = new PinyinUtils();
    System.out.println(pinyinUtils.isHanzi("内资"));
    System.out.println(pinyinUtils.isHanzi("内zi"));
    System.out.println(pinyinUtils.isHanzi("neizi"));
  }

  /**
   * 标点符号转拼音
   */
  @Test
  public void toPinyinWichPunctuation() {
    String str = "外籍自然人的护照未经公正、认证，是否可以直接作为外国投资者的“身份证明”使用";
    char[] chars = str.toCharArray();
    for (char c : chars) {
      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
      System.out.println("char:" + c + ",Array:" + Arrays.toString(pinyinArray));
    }
  }

}
