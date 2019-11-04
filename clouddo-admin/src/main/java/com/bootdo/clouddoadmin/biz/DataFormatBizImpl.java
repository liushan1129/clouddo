package com.bootdo.clouddoadmin.biz;

import com.bootdo.clouddoadmin.service.impl.ExcelExportImpl;
import com.bootdo.clouddocommon.utils.StringUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.*;

/**
 * @Author : liushan
 * @Date : 2019/2/18 2:04 PM
 */
public class DataFormatBizImpl extends ExcelExportImpl<T> {
  @Override
  protected void createExcelCell(SXSSFSheet sheet, List<T> results, Object... params) {
    int pos = 1;
    for (int i=0; i < results.size(); i++) {
      int index = 0;
      SXSSFRow dataRow = sheet.createRow(pos);
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(0)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(1)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(2)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(3)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(4)));

      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(5)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(6)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(7)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(8)));
      dataRow.createCell(index++).setCellValue(String.valueOf(results.get(9)));

      if(results.get(10) != null) {
        List<String> details = Arrays.asList(StringUtils.split(String.valueOf(results.get(10)), ","));
        if (CollectionUtils.isNotEmpty(details)) {
          for (String detail : details) {
            dataRow.createCell(index++).setCellValue(detail);
          }
        }
      }
      pos++;
    }
  }

  @Override
  protected List<String> getHeaders(String sheetName, List<T> results, Object... params) {
    List<String> headList = new ArrayList<>();
    headList.add("用户访问时间");
    headList.add("用户来源平台");
    headList.add("用户ID");
    headList.add("用户手机号");
    headList.add("用户城市");
    headList.add("用户输入内容");
    headList.add("用户是否从他人分享页面进入");
    headList.add("页面来源用户平台");
    headList.add("页面来源用户ID");
    headList.add("页面来源用户手机号");
    if (CollectionUtils.isNotEmpty(results)) {
      Map<Integer, List<String>> map = Maps.newHashMap();
      Set<Integer> sizeSet = Sets.newHashSet();
      for(int i = 0; i < results.size(); i++) {
        int sizeNum = Arrays.asList(StringUtils.split(String.valueOf(results.get(10)), ",")).size();
        sizeSet.add(sizeNum);
        map.put(sizeNum, Arrays.asList(StringUtils.split(String.valueOf(results.get(10)), ",")));
      }
      Set<Integer> sortSet = new TreeSet<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
          return o2.compareTo(o1);
        }
      });
      sortSet.addAll(sizeSet);
      Integer maxNum = (Integer) sortSet.toArray()[0];
      List<String> keys = map.get(maxNum);
      if (CollectionUtils.isNotEmpty(keys)) {
        for (String key : keys) {
          headList.add(key);
        }
      }
    }
    return headList;
  }
}
