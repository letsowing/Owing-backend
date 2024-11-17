package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.util.List;

public record StoryCrashCheckRequest(
	ProjectInfoDto project,
	List<PrevStoryInfo> prevStory,
	List<UniverseInfo> universe,
	CastInfo cast,
	ThisEpisode thisEpisode,
	Long projectId
	) {
	public static StoryCrashCheckRequest of(
		ProjectInfoDto project,
		List<PrevStoryInfo> prevStory,
		List<UniverseInfo> universe,
		CastInfo cast,
		ThisEpisode thisEpisode,
		Long projectId
	) {
		return new StoryCrashCheckRequest(project, prevStory, universe, cast, thisEpisode, projectId);
	}
}
