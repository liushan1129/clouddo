package com.bootdo.clouddoadmin.service.impl;

import com.bootdo.clouddoadmin.service.ExcelExport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @Author : liushan
 * @Date : 2019/2/18 11:48 AM
 */
@Service
public abstract class ExcelExportImpl<T> implements ExcelExport<T> {
  private String excelContentType = "application/vnd.ms-excel";
  private String excelSuffix = ".xlsx";

  @Override
  public void exportExcel(Map<String, List<T>> sheetMap, String fileName, HttpServletResponse response, Object... params) throws IOException {
    SXSSFWorkbook wb = null;
    // 第一步，创建一个webbook，对应一个Excel文件
    wb = new SXSSFWorkbook(100);
    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
    for (Map.Entry entry : sheetMap.entrySet()) {
      String sheetName = (String) entry.getKey();
      List<T> dataList = (List<T>) entry.getValue();
      SXSSFSheet sheet = wb.createSheet(sheetName);
      // 第三步，在sheet中添加表头第0行
      SXSSFRow row = sheet.createRow(0);
      // 第四步，创建单元格，并设置值表头 设置表头居中
      XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      List<String> headers = getHeaders(sheetName, dataList, params);

      for (int i = 0; i < headers.size(); i++) {
        Cell cell = row.createCell(i);
        cell.setCellValue(headers.get(i));
      }
      createExcelCell(sheet, dataList, params);
    }
    response.setContentType(excelContentType);
    response.setCharacterEncoding("utf-8");
    response.setHeader("Content-Disposition",
      "attachment;filename=" + (URLEncoder.encode(fileName, "UTF8") + excelSuffix));
    ServletOutputStream outputStream = response.getOutputStream();

    wb.write(outputStream);
    outputStream.flush();
    outputStream.close();
  }
  protected abstract void createExcelCell(SXSSFSheet sheet, List<T> results, Object... params);

  protected abstract List<String> getHeaders(String sheetName, List<T> results, Object... params);
}
