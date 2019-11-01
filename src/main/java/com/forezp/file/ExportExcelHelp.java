package com.forezp.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 
    * @ClassName: ExportExcelHelp
    * @Description: 导出Excel文件帮助类
    * @author lishuaibing
    * @date 2018年1月3日 下午3:55:16
    *
 */
public class ExportExcelHelp {

	/**
	 * 获取输出流对象
	 * 
	 * @param response			响应请求对象
	 * @param exportFileName	导出文件名
	 * @return 输出流对象
	 */
	public static ServletOutputStream getServletOutputStream(HttpServletResponse response, String exportFileName) {
		//将数据放入输出流
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition", "attachment;filename=" + exportFileName);
			out = response.getOutputStream();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("导出文件名转换编码错误！");
		} catch (IOException e) {
			throw new RuntimeException("获取输出流对象异常！");
		}
		return out;
	}
	
	/**
	 * 输出数据
	 * 
	 * @param out			输出流对象
	 * @param exportData	导出数据
	 * @throws Exception
	 */
	public static void output(ServletOutputStream out, byte[] exportData) {
		InputStream is = new ByteArrayInputStream(exportData);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
		} catch (IOException e) {
			throw new RuntimeException("文件输出失败！");
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("关闭I/O对象失败！");
			}
		}
	}
}
