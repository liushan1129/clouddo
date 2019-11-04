package com.bootdo.clouddoadmin.service;


import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author : liushan
 * @Date : 2019/2/18 11:45 AM
 */
@Service
public interface ExcelExport<T> {

  void exportExcel(Map<String, List<T>> sheetMap, String fileName, HttpServletResponse response, Object... params) throws IOException;

}
