package com.owing.ai.domains.image.dto.request;

public record GenerateCastImageRequest(
	String name,
	int age,
	String gender,
	String role,
	String description
) { }
