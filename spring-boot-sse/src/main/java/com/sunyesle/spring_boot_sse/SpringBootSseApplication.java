package com.sunyesle.spring_boot_sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringBootSseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSseApplication.class, args);
	}

}
