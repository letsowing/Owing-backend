package com.owing.ai.domains.story.dto.request;

import java.util.List;

public record CastInfo(
	List<CastList> castList,
	List<RelationList> relationList
) {
	public static CastInfo of(List<CastList> castList, List<RelationList> relationList) {
		return new CastInfo(castList, relationList);
	}
}
