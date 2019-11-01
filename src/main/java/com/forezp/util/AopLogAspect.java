package com.forezp.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forezp.exception.SummuExceptioHandler;

/**
 * 统一日志处理 Aop log
 * @author Bj
 *
 */
@Aspect
@Component
public class AopLogAspect {
	private final static Logger log = LoggerFactory.getLogger(AopLogAspect.class);

	@Pointcut("execution(public * com.icbc.sh.soft3.smmuuadmin.controller.*.*(..))")
	//两个..代表所有子目录，最后括号里的两个..代表所有参数
	public void logPointCut() {
		
	}
	
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable{
		//接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		log.info("req recv ---------------------------------------------------------------");
		//记录下请求内容
		log.info("请求地址： "+request.getRequestURL().toString());
		log.info("HTTP METHOD : "+request.getMethod());
		log.info("IP : "+request.getRemoteAddr());
		log.info("CLASS_METHOD : "+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		log.info("参数: "+Arrays.toString(joinPoint.getArgs()));
		
	}
	
	@AfterReturning(returning="ret" ,pointcut="logPointCut()")
	//returning的值和doAfterReturning的参数名一致
	public void doAfterReturning(Object ret)throws Throwable{
		//处理完请求,返回neir
		log.info("返回值："+ret);
		log.info("req end==========================================================================");
	}
	
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		Object ob = pjp.proceed();
		log.info("耗时ms:"+(System.currentTimeMillis() - startTime));
		return ob;
	}
	
	
}
