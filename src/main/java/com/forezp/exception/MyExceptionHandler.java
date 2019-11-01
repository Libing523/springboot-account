package com.forezp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("com.forezp.controller")
public class MyExceptionHandler {

	 /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder){

    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("attribute",  "The Attribute");
    }

    /**
     * 捕获CustomException
     * @param e
     * @return json格式类型
     */
    @ResponseBody
    @ExceptionHandler({BusinessException.class}) //指定拦截异常的类型
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //自定义浏览器返回状态码
    public Map<String, Object> customExceptionHandler(BusinessException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", e.getErrorCode());
        map.put("msg", e.getErrorMsg());
        return map;
    }

    /**
     * 捕获CustomException
     * @param e
     * @return 视图
     */
//    @ExceptionHandler({CustomException.class})
//    public ModelAndView customModelAndViewExceptionHandler(CustomException e) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", e.getCode());
//        map.put("msg", e.getMsg());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error");
//        modelAndView.addObject(map);
//        return modelAndView;
//    }
}
