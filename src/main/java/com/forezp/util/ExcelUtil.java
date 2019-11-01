package com.forezp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: ExcelUtil 
* @Description: 导出工具类 
* @author lishuaibing 
* @date 2018年1月12日 下午4:17:57 
*
 */
public class ExcelUtil {
	
	final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	public static synchronized XSSFWorkbook getExcelTemplate(String SheetTab, String[] titles, String[] columns,
			List<Map<String, Object>> resultList) {
		// 第一步，创建一个webbook，对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet(SheetTab);
		sheet.setColumnWidth(0, 3766); 
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		XSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		XSSFCellStyle style_title = wb.createCellStyle();
		// 设置表格头
		if (titles != null) {
			row = sheet.createRow((int) 0);
		  for (int i = 0; i < titles.length; i++) {
				row.createCell(i).setCellStyle(style_title);
				row.createCell(i).setCellValue(titles[i]);		
			}			
		}
		// 数据导入
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				row = sheet.createRow((int) i+1 );
				
				Map<String, Object> map = resultList.get(i);
				for(int j=0;j<columns.length;j++){
					XSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(map.get(columns[j])!=null?map.get(columns[j])+"":"0");
				}
			}
		}
		return wb;
	}
	
	/**
	 * 
	 * @param SheetTab
	 * @param titles
	 * @param columns
	 * @param resultList
	 * @param wb
	 * @param nullText
	 * @return
	 */
	public static synchronized XSSFWorkbook getExcelTemplate(String SheetTab, List<String> titles, List<String> columns,
			List<Map<String, Object>> resultList,XSSFWorkbook wb,String nullText)  {
		// 第一步，创建一个webbook，对应一个Excel文件
		if(wb==null){
			wb = new XSSFWorkbook();
		}
		wb.removeSheetAt(wb.getSheetIndex("Sheet1")); 
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet=wb.getSheet(SheetTab);
		XSSFRow row=null;
		XSSFCellStyle cellStyle = wb.createCellStyle();
		XSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		if(sheet==null){
			sheet = wb.createSheet(SheetTab);
			sheet.setColumnWidth(0, 3766); 
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			XSSFCellStyle style_title = wb.createCellStyle();
			// 设置表格头
			if (titles != null) {
				row = sheet.createRow((int) 0);
				for (int i = 0; i < titles.size(); i++) {
					row.createCell(i).setCellStyle(style_title);
					row.createCell(i).setCellValue(titles.get(i));		
				}			
			}
		}
		
		// 数据导入
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				row = sheet.createRow((int) i+1 );
				Map<String, Object> map = resultList.get(i);
				for(int j=0;j<columns.size();j++){
					XSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(map.get(columns.get(j))!=null?map.get(columns.get(j))+"":nullText);
				}
			}
		}
		return wb;
	}
	
	
	/**
	 * 导出excel加密（打开时需要输入密码，仅支持excel2003或以下版本）
	 * @param SheetTab
	 * @param titles
	 * @param columns
	 * @param resultList
	 * @param wb
	 * @param nullText
	 * @return
	 */
	public static synchronized HSSFWorkbook getExcelTemplate2003(String SheetTab, List<String> titles, List<String> columns,
			List<Map<String, Object>> resultList,HSSFWorkbook wb,String nullText)  {
		// 第一步，创建一个webbook，对应一个Excel文件
		if(wb==null){
			wb = new HSSFWorkbook();
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet=wb.getSheet(SheetTab);
		HSSFRow row=null;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFDataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("@"));
		if(sheet==null){
			sheet = wb.createSheet(SheetTab);
			sheet.setColumnWidth(0, 3766); 
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style_title = wb.createCellStyle();
			// 设置表格头
			if (titles != null) {
				row = sheet.createRow((int) 0);
				for (int i = 0; i < titles.size(); i++) {
					row.createCell(i).setCellStyle(style_title);
					row.createCell(i).setCellValue(titles.get(i));		
				}			
			}
		}
		
		// 数据导入
		if (resultList != null) {
			for (int i = 0; i < resultList.size(); i++) {
				row = sheet.createRow((int) i+1 );
				Map<String, Object> map = resultList.get(i);
				for(int j=0;j<columns.size();j++){
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(map.get(columns.get(j))!=null?map.get(columns.get(j))+"":nullText);
				}
			}
		}
		return wb;
	}
	
	/**
	 * 导出文件
	 * @param xssfWorkbook
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public synchronized  static void dowloadExcel(XSSFWorkbook xssfWorkbook,HttpServletRequest request,HttpServletResponse response,String fileName) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		xssfWorkbook.write(os);
		byte[] content=os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		try {
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") != -1) {// IE
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (agent != null && agent.indexOf("Mozilla") != -1) {// Firefox
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.reset();
			// response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
//		response.setContentType("text/html;charset=UTF-8");
//		response.setHeader("Content-Disposition", "attachment;filename=\""+ URLEncoder.encode(fileName,"UTF-8")+"\"");
		ServletOutputStream out = response.getOutputStream();
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(out);
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		bos.close();
	}
	
	/**
	 * 导出excel加密（打开时需要输入密码，仅支持excel2003或以下版本）
	 * @param xssfWorkbook
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public synchronized  static void dowloadExcel2003(HSSFWorkbook hssfWorkbook,HttpServletRequest request,HttpServletResponse response,String fileName) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		hssfWorkbook.writeProtectWorkbook("password", null);
		
		hssfWorkbook.write(os);
		byte[] content=os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		try {
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") != -1) {// IE
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (agent != null && agent.indexOf("Mozilla") != -1) {// Firefox
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.reset();
			// response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		ServletOutputStream out = response.getOutputStream();
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(out);
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bos.flush();
		bos.close();
	}
	
	/**
	 * 下载导入模版
	 * @param HttpServletResponse
	 * @param fileName   文件名
	 * @param inputStream    文件流
	 * @throws Exception 
	 */
	public synchronized static void downLoadTemplete(HttpServletResponse response,String fileName,InputStream  inputStream) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
        fileName=URLEncoder.encode(fileName,"UTF8"); //其他浏览器  
		try {  
			if(inputStream!=null) {
				InputStream in = inputStream;
				Workbook work = WorkbookFactory.create(in);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				work.write(os);  
				byte[] content = os.toByteArray();
				InputStream is = new ByteArrayInputStream(content);
				response.reset();
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
				ServletOutputStream out = response.getOutputStream();
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bos.flush();
			}
			
		}catch (IOException e) {
			logger.error("------>>> 模版下载失败：" + e.getMessage(),e);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}
	
	/**
	 * 下载导入模版
	 * @param HttpServletResponse
	 * @param fileName
	 * @param path
	 * @throws Exception 
	 */
	public synchronized static void downLoadTemplete(HttpServletResponse response,String fileName,String path) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
        fileName=URLEncoder.encode(fileName,"UTF8"); //其他浏览器  
		try {  
			InputStream in = new FileInputStream(new File(path));
			Workbook work = WorkbookFactory.create(in);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			work.write(os);  
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
		}catch (IOException e) {
			logger.error("------>>> 模版下载失败：" + e.getMessage(),e);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}
}
