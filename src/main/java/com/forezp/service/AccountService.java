package com.forezp.service;

import com.forezp.entity.Account;
import com.forezp.vo.AccountVO;
import com.forezp.vo.SimplePage;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
* @ClassName: AccountService 
* @Description: 业务层接口 service
* @author lishuaibing 
* @date 2018年1月11日 下午4:32:05 
*
 */
@Service
public interface AccountService {

	/**
	 * 
	* @Title: updateAccount 
	* @Description: 更新
	* @param  account
	* @return int  返回类型 
	* @throws
	 */
	public int updateAccount(Account account);
	
	/**
	 * 
	* @Title: insertAccount 
	* @Description: 插入
	* @param  account
	* @return int    返回类型 
	* @throws
	 */
	public int insertAccount(Account account);
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除 
	* @param  id
	* @return int    返回类型 
	* @throws
	 */
	public int delete(String id);
	
	/**
	 * 
	* @Title: findAccount 
	* @Description: 通过id进行查询 
	* @param  id
	* @return Account    返回类型 
	* @throws
	 */
	public Account findAccount(String id);
	
	/**
	 * 
	* @Title: selectAllAccount 
	* @Description: 查询所有 
	* @return List<Account>    返回类型 
	* @throws
	 */
	public List<Account> selectAllAccount();

	/**
	 * 
	* @Title: importExcel 
	* @Description: 导入Excel数据 
	* @param  file 文件
	* @param  isXlsx ture&&false
	* @return String    返回类型 
	* @throws
	 */
	public String importExcel(MultipartFile file, boolean isXlsx);

	/**
	 * 
	* @Title: findAccountByDetail 
	* @Description: 导出数据 
	* @param  detailListVO
	* @return List<Account>    返回类型 
	* @throws
	 */
	public List<Account> findAccountByDetail(List<Account> detailListVO);

	/**
	 * 
	* @Title: selectBySelective 
	* @Description: 分页查询数据 
	* @return List<Account>    返回类型 
	* @throws
	 */
	public PageInfo<Account> selectBySelective(AccountVO atVO,SimplePage page);
}