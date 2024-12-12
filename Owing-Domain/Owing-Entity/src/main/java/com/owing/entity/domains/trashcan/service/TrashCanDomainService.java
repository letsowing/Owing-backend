package com.owing.entity.domains.trashcan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashCanDomainService {
	private final TrashCanRepository trashCanRepository;
	private final TrashCanFolderRepository trashCanFolderRepository;
	private final TrashCanAdaptor trashCanAdaptor;

	public void deleteTrashCan(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		TrashCanFolder folder = trashCan.getTrashCanFolder();
		folder.getTrashCanList().remove(trashCan);
		trashCanRepository.delete(trashCan);
		if (folder.getTrashCanList().isEmpty()) {
			trashCanFolderRepository.delete(folder);
		}
	}

	public void createTrashCan(TrashCan trashCan) {
		trashCanRepository.save(trashCan);
		trashCanFolderRepository.updateCreatedAtById(trashCan.getTrashCanFolder().getId());
	}

	public FolderType findTableNameById(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return trashCan.getTrashCanFolder().getTableName();
	}

	public Long findItemId(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		return trashCan.getItemId();
	}
}
