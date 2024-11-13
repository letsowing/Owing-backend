package com.owing.api.story.model.dto.request;

import java.util.List;

public record StoryCrashCheckRequest(
	ProjectInfoDto project,
	List<PrevStoryInfo> prevStory,
	List<UniverseInfo> universe,
	CastInfo cast,
	String episode
) {
	public static StoryCrashCheckRequest of(
		ProjectInfoDto project,
		List<PrevStoryInfo> prevStory,
		List<UniverseInfo> universe,
		CastInfo cast,
		String episode
	) {
		return new StoryCrashCheckRequest(project, prevStory, universe, cast, episode);
	}
}
