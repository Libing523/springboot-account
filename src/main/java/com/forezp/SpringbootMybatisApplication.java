package com.forezp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.forezp.bingtest.People;

@MapperScan("com.forezp.mapper")
@SpringBootApplication
public class SpringbootMybatisApplication {

	
	 @Bean
	 @ConfigurationProperties(prefix = "com.example.demo")
	public People people() {
		return new People();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
}
