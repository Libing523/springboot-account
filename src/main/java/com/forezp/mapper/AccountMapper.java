package com.forezp.mapper;

import java.util.List;

import com.forezp.entity.Account;
import com.forezp.vo.AccountVO;
/**
 * 
* @ClassName: AccountMapper 
* @Description:  账户持久层mapper
* @author lishuaibing 
* @date 2018年1月12日 下午3:27:54 
*
 */
public interface AccountMapper {

	/**
	 * 
	* @Title: deleteByPrimaryKey 
	* @Description: 通过id进行删除
	* @param  id
	* @return int    返回类型 
	* @throws
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 
	* @Title: insert 
	* @Description: 插入数据 
	* @param  record
	* @return int    返回类型 
	* @throws
	 */
	int insert(Account record);

	/**
	 * 
	* @Title: insertSelective 
	* @Description: 插入数据 
	* @param  record
	* @return int    返回类型 
	* @throws
	 */
	int insertSelective(Account record);

	/**
	 * 
	* @Title: selectByPrimaryKey 
	* @Description: 通过id进行查询 
	* @param  id
	* @return Account    返回类型 
	* @throws
	 */
	Account selectByPrimaryKey(String id);

	/**
	 * 
	* @Title: updateByPrimaryKeySelective 
	* @Description: 更新数据 
	* @param  record
	* @return int    返回类型 
	* @throws
	 */
	int updateByPrimaryKeySelective(Account record);

	/**
	 * 
	* @Title: updateByPrimaryKey 
	* @Description: 更新数据 
	* @param  record
	* @return int    返回类型 
	* @throws
	 */
	int updateByPrimaryKey(Account record);

	/**
	 * 
	* @Title: insertBatchAccountList 
	* @Description: 批量插入数据
	* @param  list
	* @return int    返回类型 
	* @throws
	 */
	int insertBatchAccountList(List<AccountVO> list);

	/**
	 * 
	* @Title: selectAllAccount 
	* @Description: 查询所有数据 
	* @return List<Account>    返回类型 
	* @throws
	 */
	List<Account> selectAllAccount();

	/**
	 * 
	* @Title: findAccountByDetail 
	* @Description: 通过条件查询数据 
	* @param  detailListVO
	* @return List<Account>    返回类型 
	* @throws
	 */
	List<Account> findAccountByDetail(List<Account> detailListVO);

	/**
	 * 
	* @Title: selectBySelective 
	* @Description: 分页查询 数据
	* @return List<Account>    返回类型 
	* @throws
	 */
	List<Account> selectBySelective();

}
