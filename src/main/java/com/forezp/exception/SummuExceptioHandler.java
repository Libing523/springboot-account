package com.forezp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forezp.vo.Result;

//@ControllerAdvice 捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON 格式。
@ControllerAdvice(basePackages = { "com.icbc.sh.soft3.smmuuadmin.controller" })
public class SummuExceptioHandler {

	private final static Logger logger = LoggerFactory.getLogger(SummuExceptioHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	//
	public Object handle(Exception e) {
		if (e instanceof SmmuException) {
			SmmuException smmuException = (SmmuException) e;
			logger.error("返回代码：{},返回信息{}", smmuException.getCode(), smmuException.getMessage());
			logger.error("req end===============================================================");
			return Result.buildFail(smmuException.getCode(), smmuException.getMessage());
		} else {
			return Result.buildFail("", "系统异常-" + e.getMessage());
		}
	}

}
