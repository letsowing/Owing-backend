package com.owing.ai.domains.story.dto.request.crashCheck.cast;

import java.util.List;

public record CastInfo(
	List<CastList> castList,
	List<RelationList> relationList
) {
}
