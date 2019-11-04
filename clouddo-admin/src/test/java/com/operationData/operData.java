package com.operationData;

import org.junit.Test;

import java.io.*;

/**
 * @Author : liushan
 * @Date : 2019/4/4 4:27 PM
 */
public class operData {

  @Test
  public void handleData() {

    String encoding = "UTF-8";
    File file = new File("/Users/shan/document/testData.txt");
    if(file.isFile() && file.exists()) {
      System.out.println("file exists");
      try {
        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
          System.out.println(lineTxt);
        }
        read.close();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

    } else {
      System.out.println("找不到指定的文件");
    }

  }
}
