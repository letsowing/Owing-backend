package com.owing.ai.domains.image.dto.response;

import lombok.Builder;

@Builder
public record ProjectImageResponse(
	String imageUrl
) { }
