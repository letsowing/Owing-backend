package com.owing.api.universe.model.dto.response;

import com.owing.api.dnd.dto.response.FileInfoResponse;

import lombok.Builder;

@Builder
public record UniverseShortInfoResponse (
	Long id,
	String name,
	String description,
	String imageUrl
) implements FileInfoResponse { }
