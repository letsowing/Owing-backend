package com.owing.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages ={"com.owing.infrastructure", "com.owing.ai"})
public class OwingAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwingAiApplication.class, args);
	}

}
