package com.owing.api.story.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owing.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.StoryCrashRequest;
import com.owing.api.story.model.dto.request.ai.crashCheck.CastInfo;
import com.owing.api.story.model.dto.request.ai.crashCheck.CastList;
import com.owing.api.story.model.dto.request.ai.crashCheck.PrevStoryInfo;
import com.owing.api.story.model.dto.request.ai.crashCheck.ProjectInfoDto;
import com.owing.api.story.model.dto.request.ai.crashCheck.RelationList;
import com.owing.api.story.model.dto.request.ai.crashCheck.StoryCrashCheckRequest;
import com.owing.api.story.model.dto.request.ai.crashCheck.ThisEpisode;
import com.owing.api.story.model.dto.request.ai.crashCheck.UniverseInfo;
import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.CrashCheckResponse;
import com.owing.api.story.model.mapper.CrashCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.ai.log.story.service.CrashCheckLogDomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.error.StoryErrorCode;
import com.owing.entity.domains.story.error.exception.StoryException;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.dto.StoryVO;
import com.owing.entity.domains.story.service.StoryService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.projection.CastAiProjection;
import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckStoryCrashUseCase {

	private final StoryAdapter storyAdapter;
	private final UniverseAdapter universeAdapter;
	private final CastNodeAdapter castNodeAdapter;
	private final ProjectAdapter projectAdapter;

	private final OwingAiClient owingAiClient;

	// logging
	private final CrashCheckLogDomainService crashCheckLogDomainService;
	private final CrashCheckLogMapper crashCheckLogMapper;
	private final StoryService storyDomainService;


	public CrashCheckLogResponse execute(Long storyId, StoryCrashRequest dto) throws JsonProcessingException {
		Long projectId = dto.projectId();
		Project projectInfo = projectAdapter.findById(projectId);
		List<StoryVO> stories = storyDomainService.findAllByProjectId(projectId);
		List<Universe> universes = universeAdapter.findByProjectId(projectId);
		List<CastAiProjection> castNodes = castNodeAdapter.findAllCastForAiPrompt(projectId);
		List<CastRelationshipAiProjection> castRelationships = castNodeAdapter.findAllCastRelationshipForAiPrompt(projectId);

		ProjectInfoDto pdto = ProjectInfoDto.from(projectInfo);

		List<UniverseInfo> udto = universes.stream().map(UniverseInfo::from).toList();
		List<CastList> cdto = castNodes.stream().map(CastList::from).toList();
		List<RelationList> rdto = castRelationships.stream().map(RelationList::from).toList();
		CastInfo castInfo = CastInfo.of(cdto, rdto);

		List<PrevStoryInfo> sdto = stories.stream().filter(m -> !m.id().equals(storyId)).map(PrevStoryInfo::from).toList();
		ThisEpisode thisEpisode = ThisEpisode.from(stories.stream().filter(m -> m.id().equals(storyId)).findFirst().orElseThrow(() -> StoryException.of(StoryErrorCode.STORY_NOT_FOUND)));

		StoryCrashCheckRequest request = StoryCrashCheckRequest.of(pdto, sdto, udto, castInfo, thisEpisode, dto.projectId());
		CrashCheckResponse crashCheckResponse = owingAiClient.crashCheck(request);

		return logging(storyAdapter.findById(storyId), crashCheckResponse);
	}


	private CrashCheckLogResponse logging(Story story, CrashCheckResponse aiResponse) {
		CrashCheckLog crashCheckLog = crashCheckLogMapper.toEntity(story, aiResponse);
		CrashCheckLog savedLog = crashCheckLogDomainService.createLog(crashCheckLog);
		return crashCheckLogMapper.toLogResponse(savedLog);
	}
}
