package com.forezp.vo;

import java.io.Serializable;

public class SimplePage implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	/** 页码，从1开始 **/
	private int pageNum = 1;
	/** 页面大小 **/
	private int pageSize = 0;
	/** 查询count总数量 **/
	private boolean count = true;

	public SimplePage() {
		super();
	}

	public SimplePage(int pageNum, int pageSize, boolean count) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.count = count;
	}

	public SimplePage(boolean count) {
		super();
		this.count = count;
	}

	public SimplePage(int pageNum, int pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.count = true;
	}

	public boolean isCount() {
		return count;
	}

	public void setCount(boolean count) {
		this.count = count;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "SimplePage [pageNum=" + pageNum + ", pageSize=" + pageSize + ", count=" + count + "]";
	}

}
