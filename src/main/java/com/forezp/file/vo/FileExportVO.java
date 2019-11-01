package com.forezp.file.vo;

import java.util.List;


/**
 * 
    * @ClassName: FileExportVO
    * @Description: 文件导出实现VO
    * @author lishuaibing
    * @date 2018年1月3日 下午1:50:37
    *
 */
public class FileExportVO extends FileVO {

	private static final long serialVersionUID = 1L;

	/**
	 * 模板文件
	 */
	private String templateFile;
	
	/**
	 * 导出数据集
	 */
	private List<?> exportData;

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public List<?> getExportData() {
		return exportData;
	}

	public void setExportData(List<?> exportData) {
		this.exportData = exportData;
	}
	
	
	
}
