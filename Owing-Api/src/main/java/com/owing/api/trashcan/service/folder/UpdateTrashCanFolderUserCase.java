package com.owing.api.trashcan.service.folder;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;

	public void executeRestoreFolder(Long trashId) {
		trashCanFolderDomainService.restoreTrashCan(trashId);
	}
}
