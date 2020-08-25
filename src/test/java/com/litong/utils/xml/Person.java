package com.litong.utils.xml;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  private int id;
  private int sex;
  private int age;
  private String name;
  private List<Address> addList;
}