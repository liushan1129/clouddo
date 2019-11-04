package com.bootdo.clouddoadmin;

import com.bootdo.clouddoadmin.domain.UserDO;
import com.bootdo.clouddoadmin.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : liushan
 * @Date : 2018/9/25 上午10:31
 */
public class test {

  public static void main(String[] args) {
    List<UserDTO> userList = new ArrayList<>();

    UserDTO u1 = new UserDTO();
    u1.setCity("石家庄1");
    u1.setEmail("1111@11.xx");
    u1.setStatus(1);
    UserDTO u2 = new UserDTO();
    u2.setCity("石家庄2");
    u2.setEmail("1111@11.xx");
    u2.setStatus(2);
    UserDTO u3 = new UserDTO();
    u3.setCity("石家庄3");
    u3.setEmail("1111@11.xx");
    u3.setStatus(2);
    UserDTO u4 = new UserDTO();
    u4.setCity("石家庄1");
    u4.setEmail("1111@11.xx");
    u4.setStatus(2);
    userList.add(u1);
    userList.add(u2);
    userList.add(u3);
    userList.add(u4);
    boolean preTag = false;
    boolean sellingTag = false;
    boolean soldEndTag = false;

    for(UserDTO u : userList) {

      if(u.getStatus() == 1) {
        preTag = true;
      }
      if(u.getStatus() == 2) {
        sellingTag = true;
      }
      if(u.getStatus() == 3) {
        soldEndTag = true;
      }

    }
    if (soldEndTag) {
      System.out.println("1");
    } else if (preTag) {
      System.out.println("2");
    } else if (sellingTag) {
      System.out.println("3");
    } else {
      System.out.println("0");
    }


  }
}
