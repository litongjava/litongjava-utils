package com.litong.utils.string;

/**
 * @author litong
 * @date 2018年12月3日_下午5:31:42 
 * @version 1.0 
 */
public class NameUtil {

  /**
   * 获取姓氏
   * eg.李通,返回 李
   * eg.诸葛亮,返回 诸葛
   */
  public static String getFirstname(String name) {
    String[] hyphenated = { "欧阳", "太史", "端木", "上官", "司马", "东方", "独孤", "南宫", "万俟", "闻人", "夏侯", "诸葛", "尉迟", "公羊", "赫连",
        "澹台", "皇甫", "宗政", "濮阳", "公冶", "太叔", "申屠", "公孙", "慕容", "仲孙", "钟离", "长孙", "宇文", "城池", "司徒", "鲜于", "司空", "汝嫣",
        "闾丘", "子车", "亓官", "司寇", "巫马", "公西", "颛孙", "壤驷", "公良", "漆雕", "乐正", "宰父", "谷梁", "拓跋", "夹谷", "轩辕", "令狐", "段干",
        "百里", "呼延", "东郭", "南门", "羊舌", "微生", "公户", "公玉", "公仪", "梁丘", "公仲", "公上", "公门", "公山", "公坚", "左丘", "公伯", "西门",
        "公祖", "第五", "公乘", "贯丘", "公皙", "南荣", "东里", "东宫", "仲长", "子书", "子桑", "即墨", "达奚", "褚师" };
    int length = name.length();
    if (length > 2) {
      // 如果是复姓,长度大于3,取出 姓 返回
      String substring = name.substring(0, 2);
      // 取命名的前两个字,看是否在复姓库中
      for (int i = 0; i < hyphenated.length; i++) {
        if (substring.equals(hyphenated[i])) {
          return substring;
        }
      }

    } else {
      // 单姓
      return name.substring(0, 1);
    }

    if (length == 2) {
      // 单姓
      return name.substring(0, 1);
    }
    // 下面这个return不会执行
    return name;
  }
}
