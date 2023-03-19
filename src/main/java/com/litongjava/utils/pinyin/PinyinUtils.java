//package com.litongjava.utils.pinyin;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sourceforge.pinyin4j.PinyinHelper;
//
///**
// * @author Administrator
// * @date 2019年1月17日_下午4:31:16 
// * @version 1.0 
// */
//@Slf4j
//public class PinyinUtils {
//  // 汉字正则式
//  private String hanyuRegex = "[\\u4E00-\\u9FA5]+";
//  // 数字正则式
//  private String numRegex = "[0-9]";
//
//  /**
//   * char转拼音
//   * @param c
//   * @return
//   */
//  public String[] toPinyin(char c) {
//    String[] array = PinyinHelper.toHanyuPinyinStringArray(c);
//    return array;
//  }
//
//  /**
//   * String转拼音
//   */
//  public List<String[]> toPinin(String input) {
//    List<String[]> list = new ArrayList<String[]>();
//    char[] array = input.toCharArray();
//    for (char c : array) {
//      String[] pinYinArray = PinyinHelper.toHanyuPinyinStringArray(c);
//      list.add(pinYinArray);
//    }
//    return list;
//  }
//
//  /**
//   * String转拼音
//   */
//  public String toPinyinString(String input) {
//    StringBuffer stringBuffer = new StringBuffer();
//    char[] array = input.toCharArray();
//    for (char c : array) {
//      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
//
//      if (pinyinArray.length < 1) { // 返回空
//        return null;
//      }
//      // 判断最后一个元素是否是数字结尾
//      String last = pinyinArray[0].substring(pinyinArray[0].length() - 1);
//
//      if (last.matches(hanyuRegex)) { // 数字结尾,取出数字前面的内容
//        String pinyin = pinyinArray[0].substring(0, pinyinArray[0].length() - 1);
//        stringBuffer.append(pinyin);
//      } else { // 末尾没有数字
//        stringBuffer.append(pinyinArray[0]);
//      }
//    }
//    return stringBuffer.toString();
//  }
//
//  /**
//   * 如果是汉字返回true
//   */
//  public boolean isHanzi(String input) {
//    for (int i = 0; i < input.length(); i++) {
//      String current = input.substring(i, i + 1);
//      if (current.matches(hanyuRegex)) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  /**
//   * 
//   * @param string
//   * @return
//   */
//  public List<PinyinBean> toListPinyinBean(String string) {
//
//    List<PinyinBean> pinyinList = new ArrayList<>();
//    char[] chars = string.toCharArray();
//    for (char c : chars) {
//      String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
//      if (pinyinArray == null) { // 返回空
//        log.info("string:" + string);
//        log.info("返回null");
//        return null;
//      }
//      // 判断最后一个元素是否是数字结尾
//      String last = pinyinArray[0].substring(pinyinArray[0].length() - 1);
//
//      if (last.matches(numRegex)) { // 数字结尾,取出数字前面的内容
//        String pinyin = pinyinArray[0].substring(0, pinyinArray[0].length() - 1);
//        pinyinList.add(new PinyinBean(c, pinyin));
//      } else { // 末尾没有数字
//        pinyinList.add(new PinyinBean(c, pinyinArray[0]));
//      }
//    }
//    return pinyinList;
//  }
//}
