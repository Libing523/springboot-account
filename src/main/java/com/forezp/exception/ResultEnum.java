package com.forezp.exception;

public enum ResultEnum {
	SUCCESS("10000", "执行成功");

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private ResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
