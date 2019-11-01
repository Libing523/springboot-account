package com.forezp.vo;

import com.forezp.entity.Account;
import com.forezp.file.vo.ExcelModelRowVO;
import com.github.pagehelper.PageInfo;

/**
 * 
* @ClassName: AccountVO 
* @Description: 账户扩展类 
* @author lishuaibing 
* @date 2018年1月12日 下午4:18:52 
*
 */
public class AccountVO extends Account {

	/*
	 * 分页
	 */
	private SimplePage page;
	 
	private ExcelModelRowVO excelModelRowVO;

	
	
	public SimplePage getPage() {
		return page;
	}

	public void setPage(SimplePage page) {
		this.page = page;
	}

	public ExcelModelRowVO getExcelModelRowVO() {
		return excelModelRowVO;
	}

	public void setExcelModelRowVO(ExcelModelRowVO excelModelRowVO) {
		this.excelModelRowVO = excelModelRowVO;
	}
	
	
}
