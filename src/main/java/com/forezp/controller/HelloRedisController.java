package com.forezp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.forezp.dao.RedisDao;

@RestController
public class HelloRedisController {

	@Autowired
	RedisDao redisDao;
	
	@RequestMapping("/helloredis")
	@ResponseBody
	public String hello(String name, String age) {
		redisDao.setKey("name", name);
		redisDao.setKey("age", age);
		
		System.out.println("name=" + name + " * " + "age=" + age);
		
		String retName = redisDao.getValue("name");
		String retAge = redisDao.getValue("age");
		return retName + " * " + retAge;

	}
}
