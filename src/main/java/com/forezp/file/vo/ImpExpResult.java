package com.forezp.file.vo;



public class ImpExpResult<T>  {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功总数
	 */
	private int successTotal;
	
	/**
	 * 失败总数
	 */
	private int failTotal;
	
	public ImpExpResult() {
		
	}

	public ImpExpResult(int successTotal, int failTotal) {
		this.successTotal = successTotal;
		this.failTotal = failTotal;
	}

	public int getSuccessTotal() {
		return successTotal;
	}

	public void setSuccessTotal(int successTotal) {
		this.successTotal = successTotal;
	}

	public int getFailTotal() {
		return failTotal;
	}

	public void setFailTotal(int failTotal) {
		this.failTotal = failTotal;
	}
	
	
	
}
