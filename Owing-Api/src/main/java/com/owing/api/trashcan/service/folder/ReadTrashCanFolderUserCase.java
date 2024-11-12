package com.owing.api.trashcan.service.folder;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.Folder;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanFolderMapper trashCanFolderMapper;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public TrashCanFolderResponse executeProject(Long projectId) {
		return trashCanFolderMapper.toTrashCanFolderResponse(trashCanFolderDomainService.getTrashCanFolder(projectId));
	}

	public TrashCanFolder execute(Long trashId) {
		return trashCanFolderAdaptor.findById(trashId);
	}
}
