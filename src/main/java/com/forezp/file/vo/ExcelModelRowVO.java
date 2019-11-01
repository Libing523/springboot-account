package com.forezp.file.vo;

import java.util.Map;


/**
 * 
    * @ClassName: ExcelModelRowVO
    * @Description: Excel文件模型VO类
    * @author lishuaibing
    * @date 2018年1月3日 下午4:30:28
    *
 */
public class ExcelModelRowVO  {

	private static final long serialVersionUID = 1L;
	//行号
	private Integer rowNo;	
	//key:行号，value:列数据（包括列位置，列英文名称、中文名称）
	private Map<Integer, Map<String, ExcelModelVO>> rowData;
	public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
	public Map<Integer, Map<String, ExcelModelVO>> getRowData() {
		return rowData;
	}
	public void setRowData(Map<Integer, Map<String, ExcelModelVO>> rowData) {
		this.rowData = rowData;
	}	
	
	

}
