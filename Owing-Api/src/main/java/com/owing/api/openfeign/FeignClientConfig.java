package com.owing.api.openfeign;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/* open feign의 configuration은 @Configuration 을 달면 안 된다. */
public class FeignClientConfig {
	@Bean
	public OwingAiClient createClient() {
		return Feign.builder()
			.contract(new SpringMvcContract())  // Spring MVC 어노테이션을 인식하게 함
			.encoder(new JacksonEncoder())  // JacksonEncoder 사용
			.decoder(new JacksonDecoder())
			.target(OwingAiClient.class, "https://ai.letsowing.com/v1");
	}
}
