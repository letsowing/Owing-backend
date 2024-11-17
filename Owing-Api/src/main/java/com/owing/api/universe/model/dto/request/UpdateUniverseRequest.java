package com.owing.api.universe.model.dto.request;

public record UpdateUniverseRequest(
	String name,
	String description,
	String imageUrl
) { }
