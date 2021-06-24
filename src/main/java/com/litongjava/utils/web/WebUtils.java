package com.litongjava.utils.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.litongjava.utils.vo.UserModel;

public class WebUtils {

  public static final String USER_TAG = "_user";
  public static final String USER_SET_TOKEN = "X-LT-Set-User-Token";
  public static final String USER_TOKEN="X-LT-User-Token";

  /**
   * 设置当前登录用户的对象
   * @return
   */
  public static void setUser(HttpServletRequest request, UserModel user) {
    request.getSession().setAttribute(USER_TAG, user);
  }

  /**
   * 获取当前登录用户的对象
   * @return
   */
  public static UserModel getUser(HttpServletRequest request) {
    return (UserModel) request.getSession().getAttribute(USER_TAG);
  }

  public static String getUserId(HttpServletRequest request) {
    return getUser(request).getId();
  }

  /**
   * set response header for user
   * @param response
   * @param id
   */
  public static void setUser(HttpServletResponse response, String token) {
    response.setHeader(USER_SET_TOKEN, token);
  }

  /**
   * 设置登录成功后的信息
   * @param request
   * @param response
   * @param userModel
   */
  public static void setUser(HttpServletRequest request, HttpServletResponse response, UserModel user) {
    setUser(request, user);
    setUser(response, user.getId());
  }

  public static String getUserToken(HttpServletRequest request) {
    return request.getHeader(USER_TOKEN);
  }

  /**
   * 删除用户session信息
   * @param request
   */
  public static void remove(HttpServletRequest request) {
    request.getSession().removeAttribute(USER_TAG);
  }

//  /**
//   * 判断是否有权限
//   * @param code	权限代码
//   * @return
//   */
//  public static boolean hasPermission(String code) {
//    User user = getUser();
//    if (user != null && user.getId().equals("00000000000000000000000000000000")) {
//      return true;
//    }
//    String[] arr = code.split(",");
//    List<String> permissions = getPermission();
//    for (int i = 0; i < arr.length; i++) {
//      if (!permissions.contains(arr[i])) {
//        return false;
//      }
//    }
//    return true;
//  }
//
//  /**
//   * 获取当前登录用户的权限
//   * @return
//   */
//  public static List<String> getPermission() {
//    return (List<String>) getHttpServletRequest().getSession().getAttribute("systemPermission");
//  }
}
