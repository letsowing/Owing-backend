package com.owing.api.trashcan.service.folder;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;

	public void executeAll(Long projectId) {
		trashCanFolderDomainService.deleteAllTrashCan(projectId);
	}

	public void executeFolder(Long trashId) {
		trashCanFolderDomainService.deleteTrashCanFolder(trashId);
	}
}
