package com.owing.api.trashcan.service.trashcan;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteTrashCanUserCase {
	private final TrashCanDomainService trashCanDomainService;

	public void execute(Long trashId) {
		trashCanDomainService.deleteTrashCan(trashId);
	}
}

