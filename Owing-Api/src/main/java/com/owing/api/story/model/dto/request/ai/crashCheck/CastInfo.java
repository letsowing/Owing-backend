package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.util.List;

public record CastInfo(
	List<CastList> castList,
	List<RelationList> relationList
) {
	public static CastInfo of(List<CastList> castList, List<RelationList> relationList) {
		return new CastInfo(castList, relationList);
	}
}
