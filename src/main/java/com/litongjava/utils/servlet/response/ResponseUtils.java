package com.litongjava.utils.servlet.response;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * @author create by Ping E Lee on 2022年3月25日 下午1:35:36 
 *
 */
public class ResponseUtils {

  /**
   * 设置导出Excel的Http头部信息
   * @param response
   * @param filename
   */
  public static void setHeaderForDownloadExcel(HttpServletResponse response, String filename) {
    // 导出数据
    response.setContentType("application/vnd.ms-excel;charset=utf-8");
    response.setCharacterEncoding("utf-8");
    try {
      // 防止文件名乱码
      filename = new String(filename.getBytes("utf-8"), "ISO8859-1");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    response.addHeader("Content-disposition", "attachment;filename=" + filename + ExcelTypeEnum.XLSX.getValue());
  }
}
