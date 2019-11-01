package com.forezp.exception;

/**
 * 
* @ClassName: BusinessException 
* @Description: 业务异常类 
* @author lishuaibing 
* @date 2018年1月12日 下午5:25:02 
*
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2047090780838416244L;

	/** 错误码 */
	private String errorCode;

	/** 错误消息 */
	private String errorMsg;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String errorMsg) {
		super(errorMsg);
	}
	
	public BusinessException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
	}
	
	public BusinessException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public BusinessException(String errorCode, String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.errorCode = errorCode;
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
}
