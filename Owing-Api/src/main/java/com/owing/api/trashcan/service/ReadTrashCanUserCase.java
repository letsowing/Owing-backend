package com.owing.api.trashcan.service;

import org.springframework.stereotype.Service;

import com.owing.api.common.MemberUtils;
import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadTrashCanUserCase {
	private final MemberUtils memberUtils;
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanMapper trashCanMapper;


	public TrashCanFolderResponse execute(Long projectId) {
		return trashCanMapper.toTrashCanFolderResponse(trashCanFolderDomainService.getTrashCanFolder(projectId));
	}
}
