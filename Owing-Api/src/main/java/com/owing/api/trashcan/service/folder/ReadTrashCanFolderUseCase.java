package com.owing.api.trashcan.service.folder;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.dto.response.TrashCanFolderListResponse;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadTrashCanFolderUseCase {
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public TrashCanFolderListResponse executeProject(Long projectId) {
		return TrashCanFolderListResponse.from(trashCanFolderAdaptor.getTrashCanFolder(projectId));
	}

	public TrashCanFolderResponse execute(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findById(trashId);
		return TrashCanFolderResponse.from(trashCanFolder);
	}
}
