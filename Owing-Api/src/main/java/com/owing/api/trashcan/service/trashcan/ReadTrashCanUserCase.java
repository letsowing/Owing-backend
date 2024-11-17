package com.owing.api.trashcan.service.trashcan;

import com.owing.entity.domains.story.model.dto.StoryInfo;
import com.owing.entity.domains.universe.model.dto.UniverseInfo;
import com.owing.node.domains.cast.model.dto.CastInfo;
import org.apache.logging.log4j.util.Cast;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanUserCase {
	private final TrashCanMapper trashCanMapper;
	private final TrashCanAdaptor trashCanAdaptor;
	private final StoryAdapter storyAdapter;
	private final CastNodeAdapter castNodeAdapter;
	private final UniverseAdapter universeAdapter;


	public StoryInfo executeStory(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return storyAdapter.findDeletedById(trashCan.getItemId());
	}

	public CastInfo executeCast(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return castNodeAdapter.findDeletedById(trashCan.getItemId());
	}

	public UniverseInfo executeUniverse(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return universeAdapter.findDeletedById(trashCan.getItemId());
	}
}
