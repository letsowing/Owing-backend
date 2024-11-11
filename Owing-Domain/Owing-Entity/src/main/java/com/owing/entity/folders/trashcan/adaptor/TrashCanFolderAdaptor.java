package com.owing.entity.folders.trashcan.adaptor;

import java.util.List;
import java.util.Optional;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.folders.trashcan.error.TrashCanFolderErrorCode;
import com.owing.entity.folders.trashcan.error.exception.TrashCanFolderException;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class TrashCanFolderAdaptor {
	private final TrashCanFolderRepository trashCanFolderRepository;

	public List<TrashCanFolder> findAllByProject_Id(Long projectId) {
		return trashCanFolderRepository.findAllByProject_Id(projectId);
	}

	public TrashCanFolder findById(Long trashId) {
		return trashCanFolderRepository.findById(trashId).orElseThrow(() -> TrashCanFolderException.of(
			TrashCanFolderErrorCode.TRASH_CAN_FOLDER_NOT_FOUND, "요청된 TrashCanFolder Id: %d".formatted(trashId)));
	}

	public Optional<TrashCanFolder> findByItemId(Long itemId) {
		return trashCanFolderRepository.findByItemId(itemId);
	}
}
