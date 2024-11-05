package com.owing.api.trashcan.service;

import org.springframework.stereotype.Service;

import com.owing.entity.domains.project.adaptor.ProjectAdaptor;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@Service
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
