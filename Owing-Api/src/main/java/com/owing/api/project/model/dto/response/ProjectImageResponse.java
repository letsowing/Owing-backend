package com.owing.api.project.model.dto.response;

import lombok.Builder;

@Builder
public record ProjectImageResponse(
	String imageUrl
) { }
