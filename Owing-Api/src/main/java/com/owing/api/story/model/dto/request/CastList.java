package com.owing.api.story.model.dto.request;

import com.owing.node.domains.cast.model.projection.CastAiProjection;

public record CastList(
	Long castId,
	String name,
	String role,
	String gender,
	Long age,
	String description
) {
	public static CastList from(CastAiProjection cast){
		return new CastList(
			cast.castId(),
			cast.name(),
			cast.role(),
			cast.gender(),
			cast.age(),
			cast.description()
		);
	}
}
