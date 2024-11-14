package com.owing.api.story.service.story;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.CastInfo;
import com.owing.api.story.model.dto.request.CastList;
import com.owing.api.story.model.dto.request.PrevStoryInfo;
import com.owing.api.story.model.dto.request.ProjectInfoDto;
import com.owing.api.story.model.dto.request.RelationList;
import com.owing.api.story.model.dto.request.StoryCrashCheckRequest;
import com.owing.api.story.model.dto.request.StoryCrashRequest;
import com.owing.api.story.model.dto.request.UniverseInfo;
import com.owing.api.story.model.dto.response.CrashCheckResponse;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.error.StoryErrorCode;
import com.owing.entity.domains.story.error.exception.StoryException;
import com.owing.entity.domains.story.model.Story;
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
	private final ObjectMapper objectMapper;


	public CrashCheckResponse execute(Long storyId, StoryCrashRequest dto) throws JsonProcessingException {
		Long projectId = dto.projectId();
		ProjectInfo projectInfo = projectAdapter.findById(projectId).getProjectInfo();
		List<Story> stories = storyAdapter.findByProjectId(projectId);
		List<Universe> universes = universeAdapter.findByProjectId(projectId);
		List<CastAiProjection> castNodes = castNodeAdapter.findAllCastForAiPrompt(projectId);
		List<CastRelationshipAiProjection> castRelationships = castNodeAdapter.findAllCastRelationshipForAiPrompt(projectId);

		ProjectInfoDto pdto = ProjectInfoDto.from(projectInfo);
		List<PrevStoryInfo> sdto = stories.stream().filter(m -> !m.getId().equals(storyId)).map(PrevStoryInfo::from).toList();
		List<UniverseInfo> udto = universes.stream().map(UniverseInfo::from).toList();
		List<CastList> cdto = castNodes.stream().map(CastList::from).toList();
		List<RelationList> rdto = castRelationships.stream().map(RelationList::from).toList();
		CastInfo castInfo = CastInfo.of(cdto, rdto);
		String thisEpisode = stories.stream().findFirst().map(Story::getContent).orElseThrow(() -> StoryException.of(
			StoryErrorCode.STORY_NOT_FOUND));

		// fixme
		StoryCrashCheckRequest request = StoryCrashCheckRequest.of(pdto, sdto, udto, castInfo, thisEpisode);
		// System.out.println(request);
		String res = owingAiClient.crashCheck(request);
		System.out.println(res);
		res = res.replace("```json", "").replace("```","").strip();

		JsonNode rootNode = objectMapper.readTree(res);

		JsonNode itemsNode = rootNode.path("reply").path("items");

		CrashCheckResponse.Items[] itemsArray = objectMapper.treeToValue(itemsNode, CrashCheckResponse.Items[].class);

		return CrashCheckResponse.of(itemsArray);

	}
}
