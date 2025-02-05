package com.tsbgroup.practice.accountapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AccountappApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountappApplication.class, args);
	}

}
