package com.litongjava.utils.phone;

/**
 * @author litong
 * @date 2018-6-16
 *
 */
public class PhoneUtils {
  /**
   * 验证手机号码
   * 
   * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
   * 联通号码段:130、131、132、136、185、186、145
   * 电信号码段:133、153、180、189
   * @param number
   * @return
   */
  public static boolean isMobillePhone(String number) {
    String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    return check(number, regex);
  }

  private static boolean check(String cellphone, String regex) {
    if (cellphone.matches(regex))
      return true;
    return false;
  }

  /**
   * 验证固话号码
   * 
   * @param phone
   * @return
   */
  public static boolean isTelPhon(String phone) {
    String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
    return check(phone, regex);
  }
}
