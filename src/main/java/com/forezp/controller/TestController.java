package com.forezp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forezp.bingtest.People;

@RestController
public class TestController {

	@Autowired
	private People people;

	@RequestMapping("/get_name")
	public String getName() {

		return people.getName();
	}

	@RequestMapping("/get_address")
	public List<String> getAddress() {

		return people.getAddress();
	}

	@RequestMapping("/get_phone_numer")
	public String getNumber() {

		return people.getPhone().getNumber();
	}
}
