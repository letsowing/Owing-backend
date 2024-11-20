package com.owing.entity.domains.trashcan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;
import com.owing.entity.folders.trashcan.error.TrashCanFolderErrorCode;
import com.owing.entity.folders.trashcan.error.exception.TrashCanFolderException;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashCanDomainService {
	private final TrashCanRepository trashCanRepository;
	private final TrashCanFolderRepository trashCanFolderRepository;

	public void deleteTrashCan(Long trashId) {
		TrashCan trashCan = trashCanRepository.findById(trashId)
				.orElseThrow(() -> new RuntimeException("todo TrashCan not found"));
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
		TrashCan trashCan = trashCanRepository.findById(trashId)
			.orElseThrow(() -> new RuntimeException("todo TrashCan not found"));
		return trashCan.getTrashCanFolder().getTableName();
	}

	public Long findItemId(Long trashId) {
		TrashCan trashCan = trashCanRepository.findById(trashId)
			.orElseThrow(() -> new RuntimeException("todo TrashCan not found"));
		return trashCan.getItemId();
	}
}
