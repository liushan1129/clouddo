package com.Singleton;

/**
 * @Author : liushan
 * @Date : 2019/3/14 2:43 PM
 */
public class Student {

  public static void main(String[] args) {

    Singleton singleton = Singleton.getInstance();
    singleton.showMassage();
  }

}
