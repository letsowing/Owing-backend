package com.owing.api.story.model.dto.request;

import com.owing.entity.domains.universe.model.Universe;

public record UniverseInfo(
	Long universeId,
	String name,
	String description
) {
	public static UniverseInfo from(Universe universe) {
		return new UniverseInfo(
			universe.getId(),
			universe.getName(),
			universe.getDescription()
		);
	}
}
