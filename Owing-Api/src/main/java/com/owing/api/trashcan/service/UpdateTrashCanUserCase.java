package com.owing.api.trashcan.service;

import org.springframework.stereotype.Service;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanUserCase {
	private final TrashCanDomainService trashCanDomainService;

	public void executeRestore(Long trashId) {
		trashCanDomainService.restoreTrashCan(trashId);
	}
}
