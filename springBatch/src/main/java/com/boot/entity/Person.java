package com.boot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
   * 实体类
   * @author qiguangjie
   *
   */
@NoArgsConstructor
@Data
public class Person {
  //ID
  //  @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer personId;
  //姓名
  private String personName;
  //年龄
  private String personAge;
  //性别
  private String personSex;

  public Person(String personName, String personAge,
      String personSex) {
    this.personName = personName;
    this.personAge = personAge;
    this.personSex = personSex;
  }

}
