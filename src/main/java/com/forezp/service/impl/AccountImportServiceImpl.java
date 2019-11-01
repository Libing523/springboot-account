package com.forezp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forezp.file.AbstractExcelImportTemplateService;
import com.forezp.file.vo.ExcelModelVO;
import com.forezp.file.vo.ImpExpResult;
import com.forezp.mapper.AccountMapper;
import com.forezp.vo.AccountVO;

@Service("accountImportService")
public class AccountImportServiceImpl extends AbstractExcelImportTemplateService<AccountVO, String> {

	private static final Logger logger = LoggerFactory.getLogger(AccountImportServiceImpl.class);

	@Autowired
	private AccountMapper accountMapper;

	/**
	 * 跳过指定字段
	 */
	@Override
	protected String getFilterField(String filterField) {
		return null;
	}

	/**
	 * 创建账户
	 */
	@Override
	protected AccountVO getEntity() {
		return new AccountVO();
	}

	/**
	 * 映射关系
	 */
	@Override
	protected Map<String, ExcelModelVO> getExcelFieldtoEntityMapping() {

		ExcelModelVO excelId = new ExcelModelVO();
		excelId.setRowIndex(0);
		excelId.setColIndex(0);
		excelId.setColEName("id");
		excelId.setColCName("编号");

		ExcelModelVO excelName = new ExcelModelVO();
		excelName.setRowIndex(1);
		excelName.setColIndex(1);
		excelName.setColEName("name");
		excelName.setColCName("姓名");

		ExcelModelVO excelSex = new ExcelModelVO();
		excelSex.setRowIndex(2);
		excelSex.setColIndex(2);
		excelSex.setColEName("sex");
		excelSex.setColCName("性别");

		Map<String, ExcelModelVO> map = new HashMap<String, ExcelModelVO>();
		map.put("id", excelId);
		map.put("name", excelName);
		map.put("sex", excelSex);

		return map;
	}

	@Override
	protected Map<String, Boolean> getMergeColField() {
		return null;
	}

	/**
	 * 数据验证(略)
	 */
	@Override
	protected void validate(List<AccountVO> importDataList) {
		logger.info("====> AccountImportServiceImpl.validate 开始验证 importDataList.size={}", importDataList.size());
	}

	/**
	 * 批量插入数据
	 */
	@Override
	protected ImpExpResult<AccountVO> batchImport(List<AccountVO> importDataList) {
		logger.info("====> AccountImportServiceImpl.batchImport 插入数据条数 importDataList.size={}", importDataList.size());
		int addList = 0;
		try {
			addList = accountMapper.insertBatchAccountList(importDataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImpExpResult<AccountVO> restult = new ImpExpResult<>();
		restult.setSuccessTotal(addList);
		return restult;
	}

}
