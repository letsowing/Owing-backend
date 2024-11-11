package com.owing.api.trashcan.service.folder;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
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
public class ReadTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanFolderMapper trashCanFolderMapper;

	public TrashCanFolderResponse execute(Long projectId) {
		return trashCanFolderMapper.toTrashCanFolderResponse(trashCanFolderDomainService.getTrashCanFolder(projectId));
	}
}
