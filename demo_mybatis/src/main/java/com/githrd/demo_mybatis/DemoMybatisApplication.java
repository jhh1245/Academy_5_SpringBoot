package com.githrd.demo_mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication

// Mapper 위치가 basePackage 밖에 존재할 경우, 반드시 작성해야 함 
//               basePakage 내에 있을 경우 생략 가능 
@Configuration
@MapperScan(basePackages = "com.githrd.demo_mybatis", annotationClass = Mapper.class)
public class DemoMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMybatisApplication.class, args);
	}

}
