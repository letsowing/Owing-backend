package com.owing.entity.folders.trashcan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.error.TrashCanFolderErrorCode;
import com.owing.entity.folders.trashcan.error.exception.TrashCanFolderException;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashCanFolderDomainService {

	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final TrashCanFolderRepository trashCanFolderRepository;


	public List<TrashCanFolder> getTrashCanFolder(Long projectId) {
		return trashCanFolderAdaptor.findAllByProject_Id(projectId);
	}

	public void deleteAllTrashCan(Long projectId) {
		trashCanFolderRepository.deleteAllByProject_Id(projectId);
	}

	public void deleteTrashCanFolderById(Long trashId) {
		trashCanFolderRepository.deleteById(trashId);
	}

	public void createTrashCanFolder(TrashCanFolder trashCanFolder) {
		trashCanFolderRepository.save(trashCanFolder);
	}

	public FolderType findTableNameById(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderRepository.findById(trashId).orElseThrow(() -> TrashCanFolderException.of(
			TrashCanFolderErrorCode.TRASH_CAN_FOLDER_NOT_FOUND, "요청된 TrashCanFolder Id: %d".formatted(trashId)));
		return trashCanFolder.getTableName();
	}

	public List<Long> findTrashCanIdsById(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderRepository.findById(trashId).orElseThrow(() -> TrashCanFolderException.of(
			TrashCanFolderErrorCode.TRASH_CAN_FOLDER_NOT_FOUND, "요청된 TrashCanFolder Id: %d".formatted(trashId)));
		return trashCanFolder.getTrashCanList().stream()
			.map(TrashCan::getItemId) // ItemId를 추출
			.toList();
	}

	public Long findItemIdById(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderRepository.findById(trashId).orElseThrow(() -> TrashCanFolderException.of(
			TrashCanFolderErrorCode.TRASH_CAN_FOLDER_NOT_FOUND, "요청된 TrashCanFolder Id: %d".formatted(trashId)));
		return trashCanFolder.getItemId();
	}

	public void deleteTrashCanFolder(Long trashId) {
		trashCanFolderRepository.deleteById(trashId);
	}
}
