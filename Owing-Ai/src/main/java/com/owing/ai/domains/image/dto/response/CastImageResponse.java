package com.owing.ai.domains.image.dto.response;

import lombok.Builder;

@Builder
public record CastImageResponse(
	String imageUrl
) { }
