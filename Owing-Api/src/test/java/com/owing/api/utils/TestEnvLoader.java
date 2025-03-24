package com.owing.api.utils;

import org.springframework.test.context.DynamicPropertyRegistry;

import io.github.cdimascio.dotenv.Dotenv;

public class TestEnvLoader {
	public static void load(DynamicPropertyRegistry registry) {
		Dotenv dotenv = Dotenv.configure()
			.directory("../")
			.filename(".env")
			.ignoreIfMissing()
			.load();

		dotenv.entries().forEach(entry -> registry.add(entry.getKey(), entry.getValue()::toString));
	}
}
