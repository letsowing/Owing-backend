package com.owing.api.story.service.story;

import java.util.List;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.CastInfo;
import com.owing.api.story.model.dto.request.CastList;
import com.owing.api.story.model.dto.request.PrevStoryInfo;
import com.owing.api.story.model.dto.request.ProjectInfoDto;
import com.owing.api.story.model.dto.request.RelationList;
import com.owing.api.story.model.dto.request.StoryCrashCheckRequest;
import com.owing.api.story.model.dto.request.UniverseInfo;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.ProjectInfo;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckStoryCrashUseCase {

	private final StoryAdapter storyAdapter;
	private final UniverseAdapter universeAdapter;
	private final CastNodeAdapter castNodeAdapter;
	private final ProjectAdapter projectAdapter;

	private final OwingAiClient owingAiClient;


	public void execute(Long storyId, Long projectId) {
		ProjectInfo projectInfo = projectAdapter.findById(projectId).getProjectInfo();
		List<Story> stories = storyAdapter.findByProjectId(projectId);
		List<Universe> universes = universeAdapter.findByProjectId(projectId);
		List<CastNode> castNodes = castNodeAdapter.findAllByProjectId(projectId);

		ProjectInfoDto pdto = ProjectInfoDto.from(projectInfo);
		List<PrevStoryInfo> sdto = stories.stream().map(PrevStoryInfo::from).toList();
		List<UniverseInfo> udto = universes.stream().map(UniverseInfo::from).toList();
		List<CastList> cdto = castNodes.stream().map(CastList::from).toList();
		List<RelationList> rdto = null;
		CastInfo castInfo = CastInfo.of(cdto, rdto);

		StoryCrashCheckRequest request = StoryCrashCheckRequest.of(pdto, sdto, udto, castInfo, null);

		owingAiClient.crashCheck(request);
	}
}
