package com.forezp.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forezp.entity.Account;
import com.forezp.enums.AccountStatusEnum;
import com.forezp.exception.BusinessException;
import com.forezp.file.imp.IFileImportService;
import com.forezp.file.vo.ImpExpResult;
import com.forezp.mapper.AccountMapper;
import com.forezp.service.AccountService;
import com.forezp.vo.AccountVO;
import com.forezp.vo.SimplePage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
* @ClassName: AccountServiceImpl 
* @Description: 业务层接口实现类 
* @author lishuaibing 
* @date 2018年1月11日 下午4:30:04 
*
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	@Autowired
	private IFileImportService<String> accountImportService;
	@Autowired
	private AccountMapper accountMapper;

	/**
	 * 查询全部数据
	 */
	@Override
	public List<Account> selectAllAccount() {
		return accountMapper.selectAllAccount();
		
	}

	/**
	 * 通过id删除数据
	 */
	@Override
	public int delete(String id) {
		return accountMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 通过id查询数据
	 */
	@Override
	public Account findAccount(String id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	/**
	 * 插入数据
	 */
	@Override
	public int insertAccount(Account account) {
		return accountMapper.insertSelective(account);
	}

	/**
	 * 更新数据
	 */
	@Override
	public int updateAccount(Account account) {
		return accountMapper.updateByPrimaryKeySelective(account);
	}
 
	/**
	 * Excel导入数据
	 */
	public String importExcel(MultipartFile file, boolean isXlsx) {

		ImpExpResult<String> impExpResult = null;
		try {
			logger.info("====>AccountServiceImpl.importExcel 开始导入 ===> ");
			impExpResult = accountImportService.importExcel(file.getInputStream(), isXlsx);
			logger.info("共导入" + (impExpResult.getFailTotal() + impExpResult.getSuccessTotal()) + "条记录");
		} catch (Exception e) {
			throw new BusinessException(AccountStatusEnum.AC7907.toCode());
		}
		return "共导入" + (impExpResult.getFailTotal() + impExpResult.getSuccessTotal()) + "条记录";

	}

	/**
	 * Excel导出数据
	 */
	@Override
	public List<Account> findAccountByDetail(List<Account> detailListVO) {
		return accountMapper.findAccountByDetail(detailListVO);
	}

	/**
	 * 分页查询数据
	 */
	@Override
	public PageInfo<Account> selectBySelective(AccountVO atVO,SimplePage page) {
		if (page == null) {
			// 分页参数为空时，防止一次查询大数据问题，默认只查询一条
			page = new SimplePage(1, 1);
		}
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.isCount());
		
		List<Account> list = accountMapper.selectBySelective();
		PageInfo<Account> pg = ((Page<Account>) list).toPageInfo();
		
		return pg;
	}

	
}
