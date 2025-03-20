package com.owing.api.trashcan.service.folder;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteTrashCanFolderUseCase {
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public void executeAll(Long projectId) {
		trashCanFolderAdaptor.deleteAllTrashCan(projectId);
	}

	@Transactional("jpaTransactionManager")
	public void executeFolder(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findById(trashId);
		trashCanFolderAdaptor.delete(trashCanFolder);
	}
}
