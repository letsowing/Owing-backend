package com.owing.ai.domains.image.dto.request;

public record GenerateProjectImageRequest(
	String title,
	String description,
	String category,
	String[] genres
) { }
