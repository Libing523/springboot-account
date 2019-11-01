package com.forezp.vo;


/**
 * 
* @ClassName: Result 
* @Description: 返回结果类
* @author lishuaibing 
* @date 2018年1月11日 下午4:40:58 
* 
* @param <T>
 */
public class Result<T> {
	
	private Boolean isSuccess = true;
    private String errorCode;
    private String errorMsg;
    private T data;

    public Result() {
        super();
    }

    public Result(Boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public Result(Boolean isSuccess, String errorCode, String errorMsg) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Result(Boolean isSuccess, String errorCode, String errorMsg, T data) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * 构建成功结果对象，不带响应数据T
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildSuccess() {
        return new Result<T>(true, null);
    }

    /**
     * 构建成功结果对象，带响应数据T
     * @param data 响应数据
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildSuccess(T data) {
        return new Result<T>(true, data);
    }

    /**
     * 构建失败结果对象
     * @param errorCode 错误码
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildFail(String errorCode) {
        return new Result<T>(false, errorCode, null);
    }

    /**
     * 构建失败结果对象
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildFail(String errorCode, String errorMsg) {
        return new Result<T>(false, errorCode, errorMsg);
    }

    /**
     * 构建失败结果对象
     * @param errorCode 错误码
     * @param errorMsg 错误信息
     * @param data 响应数据
     * @param <T> 响应泛型
     * @return Result<T>
     */
    public static <T> Result<T> buildFail(String errorCode, String errorMsg, T data) {
        return new Result<T>(false, errorCode, errorMsg, data);
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
