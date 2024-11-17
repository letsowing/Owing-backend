package com.owing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OwingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwingBatchApplication.class, args);
	}

}
