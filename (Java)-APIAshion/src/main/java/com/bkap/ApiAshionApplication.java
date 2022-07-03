package com.bkap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication(scanBasePackages ="com.bkap")
public class ApiAshionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAshionApplication.class, args);
	}

}
