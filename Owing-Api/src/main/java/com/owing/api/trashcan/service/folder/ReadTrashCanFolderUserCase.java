package com.owing.api.trashcan.service.folder;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanFolderUserCase {
	private final TrashCanFolderMapper trashCanFolderMapper;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public TrashCanFolderResponse executeProject(Long projectId) {
		return trashCanFolderMapper.toTrashCanFolderResponse(trashCanFolderAdaptor.getTrashCanFolder(projectId));
	}

	public TrashCanFolder execute(Long trashId) {
		return trashCanFolderAdaptor.findById(trashId);
	}
}
