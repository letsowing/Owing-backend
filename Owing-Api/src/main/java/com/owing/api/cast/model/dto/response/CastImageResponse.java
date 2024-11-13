package com.owing.api.cast.model.dto.response;

import lombok.Builder;

@Builder
public record CastImageResponse(
	String imageUrl
) { }
