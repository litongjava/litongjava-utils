package com.litong.utils.poi;

import java.util.Date;

import com.litongjava.utils.date.DateFormatUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private int id;
  private String name, address;
  private int gender;
  private Date birthday;
  private String userIp;
  
  public User build() {
    User user = new User(1,"李通","北京",1,DateFormatUtils.parseToDate("1998-01-01"),"192.168.0.1");
    return user;
  }
}
