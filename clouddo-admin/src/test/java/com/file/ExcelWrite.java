package com.file;

import com.bootdo.clouddoadmin.dto.ExcelDTO;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @Author : liushan
 * @Date : 2019/2/18 5:48 PM
 */
public class ExcelWrite {

  public static void exportExcel(Map<String, List> sheetMap) throws IOException {
    SXSSFWorkbook wb = null;
    // 第一步，创建一个webbook，对应一个Excel文件
    wb = new SXSSFWorkbook(100);
    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
    for (Map.Entry entry : sheetMap.entrySet()) {
      String sheetName = (String) entry.getKey();
      List<ExcelDTO> dataList = (List<ExcelDTO>) entry.getValue();
      SXSSFSheet sheet = wb.createSheet(sheetName);
      // 第三步，在sheet中添加表头第0行
      SXSSFRow row = sheet.createRow(0);
      // 第四步，创建单元格，并设置值表头 设置表头居中
      XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      List<String> headers = getHeaders(dataList);

      for (int i = 0; i < headers.size(); i++) {
        Cell cell = row.createCell(i);
        cell.setCellValue(headers.get(i));
      }
      createExcelCell(sheet, dataList);
    }

    OutputStream outputStream = new FileOutputStream("/Users/shan/Downloads/ls111.xlsx");

    wb.write(outputStream);
    outputStream.flush();
    outputStream.close();
  }

  private static void createExcelCell(SXSSFSheet sheet, List<ExcelDTO> results) {
    int pos = 1;
    for (int i=1; i < results.size(); i++) {
      int index = 0;
      SXSSFRow dataRow = sheet.createRow(pos);
      dataRow.createCell(index++).setCellValue(results.get(i).getDate());
      dataRow.createCell(index++).setCellValue(results.get(i).getSellChannel());
      dataRow.createCell(index++).setCellValue(results.get(i).getUserId());
      dataRow.createCell(index++).setCellValue(results.get(i).getUserMobile());
      dataRow.createCell(index++).setCellValue(results.get(i).getCityName());

      dataRow.createCell(index++).setCellValue(results.get(i).getContent());
      dataRow.createCell(index++).setCellValue(results.get(i).getIsShare());
      dataRow.createCell(index++).setCellValue(results.get(i).getShareSellChannel());
      dataRow.createCell(index++).setCellValue(results.get(i).getShareUserId());
      dataRow.createCell(index++).setCellValue(results.get(i).getShareUserMobile());
      List<String> details = results.get(i).getValues();
      if (CollectionUtils.isNotEmpty(details)) {
        for (String detail : details) {
          dataRow.createCell(index++).setCellValue(detail);
        }
      }
      pos++;
    }
  }

  private static List<String> getHeaders(List<ExcelDTO> results) {
    List<java.lang.String> headList = new ArrayList<>();
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
        ExcelDTO excelDTO = results.get(i);
        sizeSet.add(excelDTO.getKeys().size());
        map.put(excelDTO.getKeys().size(), excelDTO.getKeys());

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
        for (java.lang.String key : keys) {
          headList.add(key);
        }
      }
    }
    return headList;
  }

}
