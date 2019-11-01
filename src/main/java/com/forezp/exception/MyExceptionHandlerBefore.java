package com.forezp.exception;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyExceptionHandlerBefore {

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initWebBinder(WebDataBinder binder) {
		// 对日期的统一处理
		binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
		// 添加对数据的校验
		// binder.setValidator();
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void addAttribute(Model model) {
		model.addAttribute("attribute", "The Attribute");
	}

	/**
	 * 捕获BusinessException
	 * 
	 * @param e
	 * @return json格式类型
	 *//*
	@ResponseBody
	@ExceptionHandler({ BusinessException.class }) // 指定拦截异常的类型
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 自定义浏览器返回状态码
	public Map<String, Object> businessExceptionHandler(BusinessException e) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", e.getCode());
		map.put("msg", e.getMsg());
		return map;
	}
*/
	/**
	 * 捕获BusinessException
	 * 
	 * @param e
	 * @return 视图
	 */
	// @ExceptionHandler({BusinessException.class})
	// public ModelAndView customModelAndViewExceptionHandler(BusinessException e) {
	// Map<String, Object> map = new HashMap<>();
	// map.put("code", e.getCode());
	// map.put("msg", e.getMsg());
	// ModelAndView modelAndView = new ModelAndView();
	// modelAndView.setViewName("error");
	// modelAndView.addObject(map);
	// return modelAndView;
	// }

}
