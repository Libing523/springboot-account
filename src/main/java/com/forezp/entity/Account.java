package com.forezp.entity;

/**
 * 
* @ClassName: Account 
* @Description: 用户
* @author lishuaibing 
* @date 2018年1月11日 下午4:41:59 
*
 */
public class Account {
	/*
	 * 编号id
	 */
    private String id ;
    /**
     * 姓名
     */
    private String name ;
    /**
     * 性别
     */
    private String sex;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}


}
