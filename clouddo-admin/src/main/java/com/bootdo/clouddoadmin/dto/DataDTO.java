package com.bootdo.clouddoadmin.dto;

import lombok.Data;

/**
 * @Author : liushan
 * @Date : 2019/4/9 3:29 PM
 */
public class DataDTO {
  private String name;
  private String city;
  private String actor_name;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getActor_name() {
    return actor_name;
  }

  public void setActor_name(String actor_name) {
    this.actor_name = actor_name;
  }
}
