package com.file;

import com.bootdo.clouddoadmin.dto.DataDTO;
import com.bootdo.clouddocommon.utils.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.assertj.core.util.Sets;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : liushan
 * @Date : 2019/4/9 3:27 PM
 */
public class Test1 {
  public static void main(String[] args) {
    /*try {
      MockMultipartFile multipartFile = new MockMultipartFile("data2.xlsx", new FileInputStream(new File("/Users/shan/document/data/data2.xlsx")));
      List<String[]> list = ExcelRead.readExcel(multipartFile);
      Set<Map<String, Object>> excelDTOS = Sets.newHashSet();

      for(String[] str: list) {
        System.out.println(str);
        Map<String, Object> map = Maps.newHashMap();
        if (str.length != 1 && str.length == 3) {
          map.put("data_type", 1);
          map.put("is_online", 1);
          map.put("item_title_cn", str[0]);
          map.put("title_short", str[2]);
          map.put("city_name" , str[1]);
          map.put("name", str[0]);
          map.put("show_duration", null);
          map.put("actor_name", str[2]);
          map.put("longitude", null);
          map.put("latitude", null);

        }
        if(str.length < 3) {
          map.put("data_type", 1);
          map.put("is_online", 1);
          map.put("item_title_cn", str[0]);
          map.put("title_short", null);
          map.put("city_name" , str[1]);
          map.put("name", str[0]);
          map.put("show_duration", null);
          map.put("actor_name", null);
          map.put("longitude", null);
          map.put("latitude", null);
        }
        excelDTOS.add(map);
      }
      System.out.println(JSONUtils.beanToJson(excelDTOS));
      System.out.println(excelDTOS.size());


    } catch (IOException e) {
      e.printStackTrace();
    }*/
    try {
      MockMultipartFile multipartFile = new MockMultipartFile("data1.xlsx", new FileInputStream(new File("/Users/shan/document/data/data11.xlsx")));
      List<String[]> list = ExcelRead.readExcel(multipartFile);
      Set<Map<String, Object>> excelDTOS = Sets.newHashSet();

      for(String[] str: list) {
        System.out.println("UPDATE S_RecipientAddress\n" +
                "SET IsDefault = 1\n" +
                "WHERE\n" +
                "\tRecipientMobileNo IN(\n" +
                "\t\tSELECT\n" +
                "\t\t\tt1.RecipientMobileNo\n" +
                "\t\tFROM\n" +
                "\t\t\t(\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\tRecipientMobileNo ,\n" +
                "\t\t\t\t\tisDefault ,\n" +
                "\t\t\t\t\tStatus,\n" +
                "\t\t\t\t\tCityRegionCode\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tS_RecipientAddress\n" +
                "\t\t\t\twhere\n" +
                "\t\t\t\t\tStatus = 1\n" +
                "\t\t\t\tand IsDefault = 0\n" +
                "\t\t\t\tand CityRegionCode = '" + str[0] + "'\n" +
                "\t\t\t\tgroup by\n" +
                "\t\t\t\t\tRecipientMobileNo\n" +
                "\t\t\t\thaving\n" +
                "\t\t\t\t\tcount(\n" +
                "\t\t\t\t\t\tRecipientMobileNo\n" +
                "\t\t\t\t\t) = 1\n" +
                "\t\t\t) t1\n" +
                "\t)" +
                "\tand IsDefault = 0\n" +
                "\tand Status = 1\n") ;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
