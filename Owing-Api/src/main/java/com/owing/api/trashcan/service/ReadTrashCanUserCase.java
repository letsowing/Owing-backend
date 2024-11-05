package com.owing.api.trashcan.service;

import com.owing.api.common.MemberUtils;
import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanMapper trashCanMapper;


	public TrashCanFolderResponse execute(Long projectId) {
		return trashCanMapper.toTrashCanFolderResponse(trashCanFolderDomainService.getTrashCanFolder(projectId));
	}
}
