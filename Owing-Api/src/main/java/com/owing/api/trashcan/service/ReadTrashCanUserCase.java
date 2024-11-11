package com.owing.api.trashcan.service;

import org.apache.logging.log4j.util.Cast;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanMapper trashCanMapper;
	private final TrashCanAdaptor trashCanAdaptor;
	private final StoryAdapter storyAdapter;
	//private final CastAdapter castAdapter;



	public TrashCanFolderResponse execute(Long projectId) {
		return trashCanMapper.toTrashCanFolderResponse(trashCanFolderDomainService.getTrashCanFolder(projectId));
	}

	public Story executeStory(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return storyAdapter.findById(trashCan.getItemId());
	}

	// todo cast 가져오기 구현해야함
	// public Cast executeCast(Long trashId) {
	// 	TrashCan trashCan = trashCanAdaptor.findById(trashId);
	// 	return cast.findById(trashCan.getItemId());
	// }
}
