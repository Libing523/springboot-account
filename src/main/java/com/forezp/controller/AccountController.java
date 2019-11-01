package com.forezp.controller;

import com.forezp.constant.CommonConstant;
import com.forezp.entity.Account;
import com.forezp.enums.AccountStatusEnum;
import com.forezp.exception.BusinessException;
import com.forezp.service.AccountService;
import com.forezp.util.ExcelUtil;
import com.forezp.vo.AccountVO;
import com.forezp.vo.Result;
import com.forezp.vo.SimplePage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: AccountController
 * @Description: 账户控制层
 * @author lishuaibing
 * @date 2018年1月12日 下午4:19:36
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	AccountService accountService;

	/**
	 * 查询所有数据
	 * 
	 * @return List<Account>
	 */
	@RequestMapping(value = "/list.json", method = RequestMethod.GET)
	public Result<List<Account>> getAccounts() {
		logger.info("====> AccountController.getAccounts beg   ====>");
		List<Account> accountList = null;
		try {
			accountList = accountService.selectAllAccount();
			logger.info("====> AccountController.getAccounts end accountList.size={}   ====>", accountList.size());
			return Result.buildSuccess(accountList);
		} catch (Exception e) {
			return Result.buildFail(AccountStatusEnum.AC7903.toCode(), AccountStatusEnum.AC7903.toLabel());
		}
	}
	
	
	/**
	 * 查询所有数据(分页)
	 * 
	 * @return List<Account>
	 */
	@RequestMapping(value = "/getAccountsByPage.json", method = RequestMethod.POST)
	public Result<PageInfo<Account>> getAccountsByPage(@RequestBody AccountVO atVO) {
		logger.info("====> AccountController.getAccountsByPage beg   ====>");
		PageInfo<Account> listPage = accountService.selectBySelective(atVO,atVO.getPage());
		
		logger.info("====>");
		return Result.buildSuccess(listPage);
	}
	
	
	

	/**
	 * 通过编号进行查询
	 * 
	 * @param id
	 *            编号
	 * @return Account
	 */
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	public Result<Account> getAccountById(@PathVariable("id") String id) {
		logger.info("====> AccountController.getAccountById beg id={}   ====>", id);
		Account account = new Account();
		try {
			account = accountService.findAccount(id);
			logger.info("====>AccountController.getAccountById beg name={}====>", account.getName());
			return Result.buildSuccess(account);
		} catch (Exception e) {
			return Result.buildFail(AccountStatusEnum.AC7903.toCode(), AccountStatusEnum.AC7903.toLabel());
		}
	}

	/**
	 * 通过编号进行删除
	 * 
	 * @param id
	 *            编号
	 * @return
	 */
	@RequestMapping(value = "/{id}.json", method = RequestMethod.DELETE)
	public Result<?> delete(@PathVariable(value = "id") String id) {
		logger.info("====> AccountController.delete beg id={}   ====>", id);
		try {
			accountService.delete(id);
			logger.info("====>AccountController.delete end ====>");
			return Result.buildSuccess();
		} catch (Exception e) {
			return Result.buildFail(AccountStatusEnum.AC7903.toCode(), AccountStatusEnum.AC7903.toLabel());
		}

	}

	/**
	 * 添加数据
	 * 
	 * @param accountVO
	 * @return Account
	 */
	@RequestMapping(value = "/insert.json", method = RequestMethod.POST)
	@ResponseBody
	public Result<Account> insertAccount(@RequestBody AccountVO accountVO) {
		logger.info("====> AccountController.addAccount beg 编号={},姓名={},性别={}", accountVO.getId(), accountVO.getName(),
				accountVO.getSex());
		try {
			accountService.insertAccount(accountVO);
			logger.info("====> AccountController.insertAccount end  ====>");
			return Result.buildSuccess(accountVO);
		} catch (Exception e) {
			return Result.buildFail(AccountStatusEnum.AC7903.toCode(), AccountStatusEnum.AC7903.toLabel());
		}
	}

	/**
	 * 更改数据
	 * 
	 * @param accountVO
	 * @return Account
	 */
	@RequestMapping(value = "/update.json", method = RequestMethod.POST)
	@ResponseBody
	public Result<Account> updateAccount(@RequestBody AccountVO accountVO) {
		logger.info("====>AccountController.updateAccount beg 编号={},姓名={},性别={}", accountVO.getId(),
				accountVO.getName(), accountVO.getSex());
		try {
			accountService.updateAccount(accountVO);
			logger.info("====> AccountController.updateAccount end  ====>");
			return Result.buildSuccess(accountVO);
		} catch (Exception e) {
			return Result.buildFail(AccountStatusEnum.AC7903.toCode(), AccountStatusEnum.AC7903.toLabel());
		}
	}

	/**
	 * 导入文件
	 * 
	 * @param file
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/importAccount.json", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> importAccount(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws Exception {
		logger.info("====> AccountController.importAccount 导入数据 beg   ====> ");
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (file.isEmpty()) {
			return Result.buildFail(AccountStatusEnum.AC7901.toCode(), AccountStatusEnum.AC7901.toLabel());
		}
		if (!CommonConstant.EXCEL_2007U.equals(fileType)) {
			return Result.buildFail(AccountStatusEnum.AC7902.toCode(), AccountStatusEnum.AC7902.toLabel());
		}
		try {
			String importMsg = accountService.importExcel(file, true);
			return Result.buildSuccess(importMsg);
		} catch (BusinessException e) {
			return Result.buildFail(e.getMessage());

		}
	}

	/**
	 * 导出账户信息
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/batch/exportAccountByDetail.json", method = { RequestMethod.GET })
	@ResponseBody
	public Result<?> exportAccountByDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] array = request.getParameter("ids").split(",");
		List<Account> detailListVO = new ArrayList<Account>();
		for (int i = 0; i < array.length; i++) {
			Account detail = new Account();
			detail.setId(array[i]);
			detailListVO.add(detail);
		}
		// 按id查询所有的卡券详细信息
		List<Account> detailList = accountService.findAccountByDetail(detailListVO);
		// 导出
		ClassPathResource resource = new ClassPathResource("excelTemplate/账户详细信息.xlsx");

		if (resource.isReadable() && resource.exists()) {
			try {
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(resource.getInputStream());
				List<String> columns = this.getAccountDetailEntityColumns();
				List<Map<String, Object>> dataList = this.getAccountDetailEntityList(detailList);
				ExcelUtil.getExcelTemplate("账户详细信息", this.getAccountDetailEntityTitles(), columns, dataList,
						xssfWorkbook, "");
				ExcelUtil.dowloadExcel(xssfWorkbook, request, response, "账户详细信息.xlsx");
			} catch (Exception e) {
				return Result.buildFail(AccountStatusEnum.AC7906.toCode(), AccountStatusEnum.AC7906.toLabel());
			}
		} else {
			return Result.buildFail(AccountStatusEnum.AC7906.toCode(), AccountStatusEnum.AC7906.toLabel());
		}
		return Result.buildSuccess();
	}

	/**
	 * 导出Excle标题与字段对应关系
	 * 
	 * @return List<String>
	 */
	private List<String> getAccountDetailEntityColumns() {
		ArrayList<String> columns = new ArrayList<String>();
		// 编号
		columns.add(CommonConstant.ACCOUNT_ID);
		// 姓名
		columns.add(CommonConstant.ACCOUNT_NAME);
		// 性别
		columns.add(CommonConstant.ACCOUNT_SEX);
		return columns;
	}

	/**
	 * 导出Excle标题与字段对应关系
	 * 
	 * @return List<String>
	 */
	private List<String> getAccountDetailEntityTitles() {
		ArrayList<String> columns = new ArrayList<String>();
		// 编号
		columns.add(CommonConstant.ACCOUNT_ID_EXP);
		// 姓名
		columns.add(CommonConstant.ACCOUNT_NAME_EXP);
		// 性别
		columns.add(CommonConstant.ACCOUNT_SEX_EXP);
		return columns;
	}

	/**
	 * 每个字段存值关系
	 * 
	 * @param List<CardVoucherDetailEntity>
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getAccountDetailEntityList(List<Account> list) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (list != null && list.size() > 0) {
			for (Account bean : list) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(CommonConstant.ACCOUNT_ID, bean.getId());
				data.put(CommonConstant.ACCOUNT_NAME, bean.getName());
				data.put(CommonConstant.ACCOUNT_SEX, bean.getSex());
				dataList.add(data);
			}
		}
		return dataList;
	}

	
	@RequestMapping(value = "/batch/down.json", method = { RequestMethod.GET })
	@ResponseBody
	public void downExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClassPathResource resource = new ClassPathResource("excelTemplate/账户详细信息.xlsx");
		resource.getInputStream();
		ExcelUtil.downLoadTemplete(response, "账户详细信息.xlsx", resource.getInputStream());
	}
	
}