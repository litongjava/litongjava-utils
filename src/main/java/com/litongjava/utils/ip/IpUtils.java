package com.litongjava.utils.ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtils {
  /**
   * 获取本机局域网IP地址
   */
  public static List<InetAddress> getLocalHostLANAddress() {
    List<InetAddress> retval = new ArrayList<>();
    // 1.获取所有网络接口
    Enumeration<NetworkInterface> networkInterfaces = null;
    try {
      networkInterfaces = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e1) {
      e1.printStackTrace();
    }
    // 2.遍历网络接口
    while (networkInterfaces.hasMoreElements()) {
      NetworkInterface networkInterface = networkInterfaces.nextElement();
      Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
      // 3.遍历所有网络接口下的所有IP
      while (inetAddresses.hasMoreElements()) {
        InetAddress inetAddress = inetAddresses.nextElement();
        // 4.排除loopback类型地址,取出是site-local地址
        if (isRealIp(inetAddress)) {
          retval.add(inetAddress);
        }
//        else {// 5.site-local类型的地址未被发现，先记录候选地址
//          candidateAddress = inetAddress;
//          retval.add(candidateAddress);
//        }
      }
    }

    if (retval.size() < 1) {
      // 6.如果没有发现 non-loopback地址.只能用最次选的方案
      try {
        InetAddress localHost = InetAddress.getLocalHost();
        retval.add(localHost);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
    }

    // 7.最后返回
    return retval;
  }

  /**
   * 判断是否为可以输出的ip地址
   */
  private static boolean isRealIp(InetAddress inetAddress) {
//    System.out.println(inetAddress.getHostAddress());
//    System.out.println(inetAddress.isAnyLocalAddress());
//    System.out.println(inetAddress.isLinkLocalAddress());
//    System.out.println(inetAddress.isLoopbackAddress());
//    System.out.println(inetAddress.isMCGlobal());
//    System.out.println(inetAddress.isMCLinkLocal());
//    System.out.println(inetAddress.isMCNodeLocal());
//    System.out.println(inetAddress.isMCOrgLocal());
//    System.out.println(inetAddress.isMCSiteLocal());
//    System.out.println(inetAddress.isMulticastAddress());
//    System.out.println(inetAddress.isSiteLocalAddress());

    return inetAddress.isSiteLocalAddress() && !inetAddress.getHostAddress().equals("172.17.0.1");
  }

  private static String toHexString(int i) {
    // 将得来的int类型数字转化为十六进制数
    String str = Integer.toHexString((int) (i & 0xff));
    // 如果遇到单字符，前置0占位补满两格
    if (str.length() == 1) {
      str = "0" + str;
    }
    return str;
  }

  @SuppressWarnings("static-access")
  public static String getMac(InetAddress inetAddress) {
    NetworkInterface byInetAddress = null;
    try {
      byInetAddress = NetworkInterface.getByInetAddress(inetAddress.getLocalHost());
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    byte[] hardwareAddress = null;
    try {
      hardwareAddress = byInetAddress.getHardwareAddress();
    } catch (SocketException e) {
      e.printStackTrace();
    }
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < hardwareAddress.length; i++) {
      String hexString = toHexString(hardwareAddress[i]);
      if (i < hardwareAddress.length - 1) {
        stringBuffer.append(hexString + "-");
      } else {
        stringBuffer.append(hexString);
      }
    }
    return stringBuffer.toString();
  }

  /**
   * 输出访问地址
   * 
   * @param port
   * @param contextPath
   */
  public static String[] getThisUrl(int port, String contextPath) {
    List<InetAddress> localHostLANAddress = getLocalHostLANAddress();
    String[] retval = new String[localHostLANAddress.size()];

    for (int i = 0; i < localHostLANAddress.size(); i++) {
      InetAddress inetAddress = localHostLANAddress.get(i);
      String message="http://" + inetAddress.getHostAddress() + ":" + port + contextPath;
      retval[i] = message;
      System.out.println(message);
    }

    return retval;
  }
}