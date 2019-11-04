package com.file;

import com.bootdo.clouddoadmin.dto.ExcelDTO;
import com.bootdo.clouddocommon.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author : liushan
 * @Date : 2019/2/18 5:14 PM
 */
public class ExportTest {

  public static void main(String[] args) {
    try {
      MockMultipartFile multipartFile = new MockMultipartFile("20190428172801.xlsx", new FileInputStream(new File("/Users/shan/Downloads/201904281728.xlsx")));
      List<String[]> list = ExcelRead.readExcel(multipartFile);
      List<ExcelDTO> excelDTOS = Lists.newArrayList();

      for(String[] str: list) {
        if (str.length != 1) {
          String nameStr = str[str.length - 1];
          Map<String, String> chooseMap = null;
          List<String> keys = Lists.newArrayList();
          List<String> values = Lists.newArrayList();

          if (StringUtils.isNoneEmpty(nameStr)) {

            String names = StringUtils.substring(nameStr, 1, nameStr.length() - 1);

            List<String> nameList = Arrays.asList(StringUtils.split(names, ","));

            chooseMap = Maps.newHashMap();
            for (int i = 0; i < nameList.size(); i++) {
              int a = i + 1;
              chooseMap.put("选项" + a, nameList.get(i));
              keys.add("选项" + a);
              values.add(nameList.get(i));
            }
          }
          ExcelDTO excelDTO = new ExcelDTO();
          excelDTO.setDate(str[0]);
          excelDTO.setSellChannel(str[1]);
          excelDTO.setUserId(str[2]);
          excelDTO.setUserMobile(str[3]);
          excelDTO.setCityName(str[4]);
          excelDTO.setContent(str[5]);
          excelDTO.setIsShare(str[6]);
          excelDTO.setShareSellChannel(str[7]);
          excelDTO.setShareUserId(str[8]);
          excelDTO.setShareUserMobile(str[9]);
          excelDTO.setChooseMap(chooseMap);
          excelDTO.setKeys(keys);
          excelDTO.setValues(values);
          excelDTOS.add(excelDTO);
        }
      }
      Map<String, List>  map = Maps.newHashMap();
      map.put("文件下载", excelDTOS);
      ExcelWrite.exportExcel(map);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
