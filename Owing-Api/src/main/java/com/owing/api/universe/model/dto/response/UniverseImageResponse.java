package com.owing.api.universe.model.dto.response;

import lombok.Builder;

@Builder
public record UniverseImageResponse(
	String imageUrl
) { }
