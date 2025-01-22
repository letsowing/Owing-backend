package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.util.List;

import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.node.domains.cast.model.projection.CastAiProjection;
import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;

public record StoryCrashCheckRequest(
	ProjectInfoDto project,
	List<PrevStoryInfo> prevStory,
	List<UniverseInfo> universe,
	CastInfo cast,
	ThisEpisode thisEpisode,
	Long projectId
	) {
	public static StoryCrashCheckRequest of(
		Project projectInfo,
		List<Story> stories,
		List<Universe> universes,
		List<CastAiProjection> castNodes,
		List<CastRelationshipAiProjection> castRelationships,
		Story story,
		Long projectId
	) {
		ProjectInfoDto pdto = ProjectInfoDto.from(projectInfo);

		List<PrevStoryInfo> sdto = stories.stream().filter(m -> !m.getId().equals(story.getId())).map(PrevStoryInfo::from).toList();
		List<UniverseInfo> udto = universes.stream().map(UniverseInfo::from).toList();
		List<CastList> cdto = castNodes.stream().map(CastList::from).toList();
		List<RelationList> rdto = castRelationships.stream().map(RelationList::from).toList();
		CastInfo castInfo = CastInfo.of(cdto, rdto);

		ThisEpisode thisEpisode = ThisEpisode.from(story);


		return new StoryCrashCheckRequest(pdto, sdto, udto, castInfo, thisEpisode, projectId);
	}
}
