package com.litongjava.utils.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;

/**
 * @author create by ping-e-lee on 2021年7月9日 下午6:33:09 
 * @version 1.0 
 * @desc
 */
public class IPUtilsTest {

  @Test
  public void test() {
     List<InetAddress> localHostLANAddress = IpUtils.getLocalHostLANAddress();
    for(int i=0;i<localHostLANAddress.size();i++) {
      System.out.println(localHostLANAddress.get(i).getHostAddress());
    }
  }
  @Test
  public void getLocalHost() {
    InetAddress localHost=null;
    try {
      localHost = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    System.out.println(localHost);
    System.out.println(localHost.getHostAddress());
  }

}
