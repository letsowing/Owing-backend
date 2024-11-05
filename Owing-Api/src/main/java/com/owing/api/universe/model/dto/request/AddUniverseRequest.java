package com.owing.api.universe.model.dto.request;

public record AddUniverseRequest (
	Long folderId,
	String name,
	String description,
	String imageUrl
) { }
