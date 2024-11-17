package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;

import com.owing.entity.domains.universe.model.Universe;

public record UniverseInfo(
	Long id,
	String name,
	String description,
	LocalDateTime updatedAt
) {
	public static UniverseInfo from(Universe universe) {
		return new UniverseInfo(
			universe.getId(),
			universe.getName(),
			universe.getDescription(),
			universe.getUpdatedAt()
		);
	}
}
