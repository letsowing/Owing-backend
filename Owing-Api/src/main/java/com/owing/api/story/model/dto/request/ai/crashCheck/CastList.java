package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;

import com.owing.node.domains.cast.model.projection.CastAiProjection;

public record CastList(
	Long id,
	String name,
	String role,
	String gender,
	Long age,
	String description,
	LocalDateTime updatedAt
) {
	public static CastList from(CastAiProjection cast){
		return new CastList(
			cast.castId(),
			cast.name(),
			cast.role(),
			cast.gender(),
			cast.age(),
			cast.description(),
			cast.updatedAt()
		);
	}
}
