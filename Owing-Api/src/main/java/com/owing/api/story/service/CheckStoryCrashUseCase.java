package com.owing.api.story.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.owing.api.story.model.dto.request.StoryCrashRequest;
import com.owing.api.story.model.dto.request.ai.crashCheck.StoryCrashCheckRequest;
import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.CrashCheckResponse;
import com.owing.api.story.model.mapper.CrashCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.adapter.CrashCheckLogAdapter;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.projection.CastAiProjection;
import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;
import com.owing.openfeign.OwingAiClient;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckStoryCrashUseCase {

	private final StoryAdapter storyAdapter;
	private final UniverseAdapter universeAdapter;
	private final CastNodeAdapter castNodeAdapter;
	private final ProjectAdapter projectAdapter;
	private final CrashCheckLogAdapter crashCheckLogAdapter;

	private final OwingAiClient owingAiClient;

	private final CrashCheckLogMapper crashCheckLogMapper;


	public CrashCheckLogResponse execute(Long storyId, StoryCrashRequest dto) throws JsonProcessingException {
		Long projectId = dto.projectId();
		Project projectInfo = projectAdapter.findById(projectId);
		List<Story> stories = storyAdapter.findByProjectId(projectId);
		List<Universe> universes = universeAdapter.findByProjectId(projectId);
		List<CastAiProjection> castNodes = castNodeAdapter.findAllCastForAiPrompt(projectId);
		List<CastRelationshipAiProjection> castRelationships = castNodeAdapter.findAllCastRelationshipForAiPrompt(projectId);
		Story story = storyAdapter.findById(storyId);

		StoryCrashCheckRequest request = StoryCrashCheckRequest.of(projectInfo, stories, universes, castNodes, castRelationships, story, dto.projectId());
		CrashCheckResponse crashCheckResponse = owingAiClient.crashCheck(request);

		return logging(story, crashCheckResponse);
	}


	private CrashCheckLogResponse logging(Story story, CrashCheckResponse aiResponse) {
		CrashCheckLog crashCheckLog = crashCheckLogMapper.toEntity(story, aiResponse);
		CrashCheckLog savedLog = crashCheckLogAdapter.save(crashCheckLog);
		return crashCheckLogMapper.toLogResponse(savedLog);
	}
}
