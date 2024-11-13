package com.owing.api.project.model.dto.request;

public record GenerateProjectImageRequest(
	String title,
	String description,
	String category,
	String[] genres
) { }
