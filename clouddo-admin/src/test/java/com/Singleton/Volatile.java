package com.Singleton;

/**
 * @Author : liushan
 * @Date : 2019/4/1 11:43 AM
 */
public class Volatile {

    public volatile int inc = 0;

    public void increase() {
      inc++;
    }

    public static void main(String[] args) {
      final Volatile test = new Volatile();
      for(int i=0;i<10;i++){
        new Thread(){
          public void run() {
            for(int j=0;j<100;j++)
              test.increase();
          };
        }.start();
      }

      while(Thread.activeCount()>1)  //保证前面的线程都执行完
        Thread.yield();
      System.out.println(test.inc);
    }
}
