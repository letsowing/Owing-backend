package com.owing.api.cast.model.dto.request;

public record GenerateCastImageRequest(
	String name,
	int age,
	String gender,
	String role,
	String description
) { }
