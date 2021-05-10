package com.springboot.app.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringbootServiceEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServiceEcommerceApplication.class, args);
	}

}
