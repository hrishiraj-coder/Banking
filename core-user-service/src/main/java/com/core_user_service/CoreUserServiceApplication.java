package com.core_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoreUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreUserServiceApplication.class, args);
	}

}
