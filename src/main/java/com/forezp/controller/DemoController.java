package com.forezp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.forezp.exception.BusinessException;

@Controller
public class DemoController {

	/**
	 * 关于@ModelAttribute, 可以使用ModelMap以及@ModelAttribute()来获取参数值。
	 */
	@GetMapping("/one")
	public String testError(ModelMap modelMap) {
		throw new BusinessException("400", "系统发生400异常！" + modelMap.get("attribute"));
	}

	@GetMapping("/two")
	public String testTwo(@ModelAttribute("attribute") String attribute) {
		throw new BusinessException("404", "系统发生404异常！" + attribute);
	}
}
