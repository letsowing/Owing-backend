package com.owing.api.universe.model.dto.response;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;

import lombok.Builder;

@Builder
public record UniverseShortInfoResponse (
	Long id,
	String name,
	String description,
	String imageUrl
) implements DndInfoResponse { }
