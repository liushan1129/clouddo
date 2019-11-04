package com.Singleton;


import org.springframework.stereotype.Controller;

/**
 * @Author : liushan
 * @Date : 2019/3/14 2:32 PM
 */

public class Singleton {
  //饿汉式
  /*
  private static Singleton instance = new Singleton();
  private Singleton(){}
  public static Singleton getInstance() {
    return instance;
  }
  public void showMessage() {
    System.out.println("I am a student");
  }*/


  //懒汉式
 /*
  private static volatile Singleton instance;
  private Singleton(){}
  public static Singleton getInstance() {
    if(instance == null) {
      synchronized (Singleton.class) {
        if(instance == null) {
          instance = new Singleton();
        }
      }
    }
    return instance;
  }*/
 //内部静态类
  private static class SingletonHandler {
    private static final Singleton INSTANCE = new Singleton();
  }
  private Singleton(){}

  public static Singleton getInstance() {
    return SingletonHandler.INSTANCE;
  }
  public void showMassage() {
    System.out.println("are you a student?");
  }

}
