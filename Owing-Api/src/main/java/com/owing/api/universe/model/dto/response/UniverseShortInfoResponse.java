package com.owing.api.universe.model.dto.response;

import lombok.Builder;

@Builder
public record UniverseShortInfoResponse (
	Long id,
	String name,
	String description,
	String imageUrl
) { }
