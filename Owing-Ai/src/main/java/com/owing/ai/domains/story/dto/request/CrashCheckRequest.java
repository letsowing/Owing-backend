package com.owing.ai.domains.story.dto.request;

import java.util.List;

public record CrashCheckRequest(
	ProjectInfoDto project,
	List<PrevStoryInfo> prevStory,
	List<UniverseInfo> universe,
	CastInfo cast,
	String thisEpisode
) {
}
