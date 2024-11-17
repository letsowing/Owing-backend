package com.owing.ai.domains.story.dto.request.crashCheck;

import java.util.List;

import com.owing.ai.domains.story.dto.request.crashCheck.cast.CastInfo;
import com.owing.ai.domains.story.dto.request.crashCheck.project.ProjectInfoDto;
import com.owing.ai.domains.story.dto.request.crashCheck.story.PrevStoryInfo;
import com.owing.ai.domains.story.dto.request.crashCheck.story.ThisEpisode;
import com.owing.ai.domains.story.dto.request.crashCheck.universe.UniverseInfo;

public record CrashCheckRequest (
	ProjectInfoDto project,
	List<PrevStoryInfo> prevStory,
	List<UniverseInfo> universe,
	CastInfo cast,
	ThisEpisode thisEpisode
) {

}
