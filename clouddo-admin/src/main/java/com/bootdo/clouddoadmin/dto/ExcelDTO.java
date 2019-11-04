package com.bootdo.clouddoadmin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author : liushan
 * @Date : 2019/2/18 4:18 PM
 */
public class ExcelDTO {
  private String date;
  private String sellChannel;
  private String userId;
  private String userMobile;
  private String cityName;
  private String content;
  private String isShare;
  private String shareSellChannel;
  private String shareUserId;
  private String shareUserMobile;
  private Map<String, String> chooseMap;
  private List<String> keys;
  private List<String> values;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getSellChannel() {
    return sellChannel;
  }

  public void setSellChannel(String sellChannel) {
    this.sellChannel = sellChannel;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserMobile() {
    return userMobile;
  }

  public void setUserMobile(String userMobile) {
    this.userMobile = userMobile;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getIsShare() {
    return isShare;
  }

  public void setIsShare(String isShare) {
    this.isShare = isShare;
  }

  public String getShareSellChannel() {
    return shareSellChannel;
  }

  public void setShareSellChannel(String shareSellChannel) {
    this.shareSellChannel = shareSellChannel;
  }

  public String getShareUserId() {
    return shareUserId;
  }

  public void setShareUserId(String shareUserId) {
    this.shareUserId = shareUserId;
  }

  public String getShareUserMobile() {
    return shareUserMobile;
  }

  public void setShareUserMobile(String shareUserMobile) {
    this.shareUserMobile = shareUserMobile;
  }

  public Map<String, String> getChooseMap() {
    return chooseMap;
  }

  public void setChooseMap(Map<String, String> chooseMap) {
    this.chooseMap = chooseMap;
  }

  public List<String> getKeys() {
    return keys;
  }

  public void setKeys(List<String> keys) {
    this.keys = keys;
  }

  public List<String> getValues() {
    return values;
  }

  public void setValues(List<String> values) {
    this.values = values;
  }
}
