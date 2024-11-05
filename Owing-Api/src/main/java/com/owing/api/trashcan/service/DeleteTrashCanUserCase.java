package com.owing.api.trashcan.service;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteTrashCanUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final TrashCanDomainService trashCanDomainService;

	public void executeAll(Long projectId) {
		trashCanFolderDomainService.deleteAllTrashCan(projectId);
	}

	public void execute(Long trashId) {
		trashCanDomainService.deleteTrashCan(trashId);
	}

	public void executeFolder(Long trashId) {
		trashCanFolderDomainService.deleteTrashCanFolder(trashId);
	}
}
