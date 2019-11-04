package com.order;

import com.google.common.collect.Iterables;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author : liushan
 * @Date : 2019/3/12 3:52 PM
 */
public class BubbleOrder {

  @Test
  public void bubbleOrder() {
    int[] arr = {12, 45, 9, 67, 455};
    for(int i = 0; i < arr.length - 1; i++) {
      if(arr[i] > arr[i+1]) {
        int temp;
        temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
      }
    }
    for(int i = 0 ; i < arr.length ; i++) {
      System.out.println(arr[i]);
    }
  }

  @Test
  public void bubbleOrderV2() {
    int[] arr = {12, 45, 9, 455, 67};
    for(int i = 0; i < arr.length - 1; i++) {
      for(int j = 0; j < arr.length - 1 - i; j++) {

        if(arr[j] > arr[j + 1]) {
          int temp;
          temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
    for(int i = 0 ; i < arr.length ; i++) {
      System.out.println(arr[i]);
    }
  }


  @Test
  public void removeIf() {
    List<String> list = Lists.newArrayList();
    list.add("说什么");
    list.add("吃什么");
    list.add("看什么");
    list.add("玩什么");
    list.add("想什么");
    list.add("是什么");
    Iterables.removeIf(list, s ->{
      if(s == null) {
        return true;
      }
      if(s.equals("吃什么")) {
        return true;
      }
      return false;
    });
    for(String s1 : list) {
      System.out.println(s1);
    }
  }

  @Test
  public void compareTo() {
    BigDecimal a = BigDecimal.valueOf(100);
    BigDecimal b = BigDecimal.valueOf(200);
    System.out.println(a.compareTo(b));


  }

}
