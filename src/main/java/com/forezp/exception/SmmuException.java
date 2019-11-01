package com.forezp.exception;

public class SmmuException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SmmuException(String code,String message) {
		super(message);
		this.code = code;
	}
	
	public SmmuException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}

	
}
