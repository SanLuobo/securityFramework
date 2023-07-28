package com.x.framework;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@MapperScan(basePackages = {"com.x.framework.secuity.config.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
