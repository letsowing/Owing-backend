package com.owing.api.story.model.dto.request;

import com.owing.node.domains.cast.model.CastNode;

public record CastList(
	Long castId,
	String name,
	String role,
	String gender,
	Long age,
	String description
) {
	public static CastList from(CastNode cast){
		return new CastList(
			cast.getId(),
			cast.getName(),
			cast.getRole(),
			cast.getGender(),
			cast.getAge(),
			cast.getDescription()
		);
	}
}
