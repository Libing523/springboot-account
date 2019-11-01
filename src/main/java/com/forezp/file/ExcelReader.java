package com.forezp.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取excel工具类
 * 
 * @author lishuaibing
 *
 */
public class ExcelReader {
	
	private Logger logger = LoggerFactory.getLogger(ExcelReader.class);

	private File excelFile;
	private InputStream inputStream;
	private Workbook wb;
	private Sheet sheet;
	private boolean isXlsx;
	// 默认读第一个工作表
	private int sheetIndex = 0;
	// 默认第一行为标题行
	private int titleRowNum = 0;

	private int rowCount = 0;

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	private String[] titles = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public ExcelReader() {
	}
	
	/**
	 * 构造函数
	 * @param file
	 */
	public ExcelReader(File file) {
		excelFile = file;
	}
	
	/**
	 * 构造函数
	 * @param inputStream
	 * @param isXlsx 是否2003以上版本
	 */
	public ExcelReader(InputStream inputStream, boolean isXlsx) {
		this.inputStream = inputStream;
		this.isXlsx = isXlsx;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setXlsx(boolean isXlsx) {
		this.isXlsx = isXlsx;
	}

	/**
	 * 设置需要读取的sheet
	 * @param sheetIndex
	 */
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	/**
	 * 设置标题行所在位置
	 * @param titleRowNum
	 */
	public void setTitleRowNum(int titleRowNum) {
		this.titleRowNum = titleRowNum;
	}

	public Workbook getWorkbook(){
		return wb;
	}
	/**
	 * 打开excel
	 */
	public void open() {
		try {
			// 文件名为空,通过文件流的形式读取
			String postfix = null;
			if (excelFile != null){
				postfix = getPostfix(excelFile.getName());
			}
			if (excelFile == null && inputStream != null) {
				if (isXlsx) {
					wb = new XSSFWorkbook(inputStream);
				} else {
					wb = new HSSFWorkbook(inputStream);
				}
			}else {
				// 通过文件名读取
				if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					wb = new HSSFWorkbook(new FileInputStream(excelFile));
				} else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
					wb = new XSSFWorkbook(new FileInputStream(excelFile));
				}
			}
			sheet = wb.getSheetAt(sheetIndex);
			rowCount = sheet.getLastRowNum() + 1;
		} catch (IOException e) {
			String error = "文件读取失败！";
			logger.error(error + e.getMessage(), e);
			throw new RuntimeException(error);
		}
	}
	
	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public String getPostfix(String fileName) {
		if (fileName == null || EMPTY.equals(fileName.trim())) {
			return EMPTY;
		}
		if (fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}
		return EMPTY;
	}

	/**
	 * 取得工作表的数据总行数
	 * 
	 * @param sheetIndex
	 * @return
	 * @throws Exception
	 */
	public int getRowsCount() {
		return rowCount;
	}

	/**
	 * 读取标题行
	 * 
	 * @return
	 */
	public String[] getTitle() throws Exception {
		if(this.getWorkbook()==null){
			this.open();
		}
		if (titles == null) {
			if (sheet == null) {
				throw new Exception("sheet工作表未打开或者定义有误");
			}
			Row row = sheet.getRow(titleRowNum);
			int index = 0;
			int lastCellNum = row.getLastCellNum();
			String[] title = new String[lastCellNum];
			while (index < lastCellNum) {
				Cell cell = row.getCell(index);
				String cellName = cell.getStringCellValue();
				title[index] = cellName == null ? "" : cellName.trim();
				index++;
			}
			titles = title;
		}
		return titles;
	}

	/**
	 * 读每一行的数据,返回数组形式
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @return
	 * @throws Exception
	 */
	public String[] getRowData(int rowIndex) throws Exception {
		if (sheet == null) {
			throw new Exception("sheet工作表未打开或者定义有误");
		}
		Row row = sheet.getRow(rowIndex);
		int index = 0;
		int lastCellNum = row.getLastCellNum();
		String[] rowData = new String[lastCellNum];
		while (index < lastCellNum) {
			Cell cell = row.getCell(index);
			String cellName = cell.getStringCellValue();
			rowData[index] = cellName == null ? "" : cellName.trim();
			index++;
		}
		return rowData;
	}

	/**
	 * 读某一行的数据，返回该行所有列-值对应的Map
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getRowDataMap(int rowIndex) throws Exception {

		Map<String, String> rowMap = new HashMap<String, String>();
		if (sheet == null) {
			throw new Exception("sheet工作表未打开或者定义有误");
		}
		Row row = sheet.getRow(rowIndex);
		if(row==null){
			return null;
		}
//		System.out.println(rowIndex);
		// 列下标
		int cellIndex = 0;
//		// 最后一列
//		int lastCellNum = row.getLastCellNum();
		// 标题
		String[] titles = this.getTitle();

		while (cellIndex < titles.length) {
			Cell cell = row.getCell(cellIndex);
			String value = null;
			if (cell != null) {
				if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date dateValue = cell.getDateCellValue();
						value = sdf.format(dateValue);
					} else {
						value = cell.getNumericCellValue() + "";
					}
				} else {
					String cellName = cell.getStringCellValue();
					value = cellName == null ? "" : cellName.trim();
				}

			}
			rowMap.put(titles[cellIndex], value);
			cellIndex++;
		}
		return rowMap;
	}
	
	/**
	 * 获取行数据
	 * @param rowIndex	行号
	 * @return	行数据
	 * @throws Exception
	 * @author lishuaibing
	 * @date 2017-8-4
	 */
	public Map<String, Object> getRow(int rowIndex) throws Exception {
		Map<String, Object> rowMap = new HashMap<String, Object>();
		if (sheet == null) {
			throw new Exception("sheet工作表未打开或者定义有误");
		}
		Row row = sheet.getRow(rowIndex);
		if (rowIndex == 0 && row == null) {
			throw new RuntimeException("第"+ (rowIndex+1) +"行不是标题行，请修改模版！");
		} else if (row == null) {
			return null;
		}
		int cellIndex = 0;
		
		// 标题
		//只有读标题行时才获取标题  updated by lishuaibing on 2017-8-4
		if (rowIndex == 0) {
			titles = this.getRowData(titleRowNum);
		}

		while (cellIndex < titles.length) {
			Cell cell = row.getCell(cellIndex);
			Object value = null;
			if (cell != null) {
				if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						value = cell.getDateCellValue();
					} else {
						value = cell.getNumericCellValue() + "";
					}
				} else {
					String cellName = cell.getStringCellValue();
					value = cellName == null ? "" : cellName.trim();
				}

			}
			rowMap.put(titles[cellIndex], value);
			cellIndex++;
		}
		return rowMap;
	}

}
