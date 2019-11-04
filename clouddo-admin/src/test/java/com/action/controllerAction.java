package com.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author : liushan
 * @Date : 2019/3/14 3:58 PM
 */
@Controller
public class controllerAction {

  private int singletonInt=1;
  @RequestMapping(value = "/test")
  @ResponseBody
  public String singleton(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
    String data=request.getParameter("data");
    if(data!=null && data.length()>0){
      try{
        int paramInt= Integer.parseInt(data);
        singletonInt = singletonInt + paramInt;
      }
      catch(Exception ex){
        singletonInt+=10;
      }
    }else{
      singletonInt+=1000;
    }
    return String.valueOf(singletonInt);
  }
}
