package com.owing.entity.domains.trashcan.adaptor;

import java.util.List;
import java.util.Optional;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.domains.trashcan.error.TrashCanFolderErrorCode;
import com.owing.entity.domains.trashcan.error.exception.TrashCanFolderException;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;
import com.owing.entity.domains.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class TrashCanFolderAdaptor {
	private final TrashCanFolderRepository trashCanFolderRepository;

	public List<TrashCanFolder> getTrashCanFolder(Long projectId) {
		return trashCanFolderRepository.findAllByProject_Id(projectId);
	}

	public TrashCanFolder findById(Long trashId) {
		return trashCanFolderRepository.findById(trashId)
			.orElseThrow(() -> TrashCanFolderException.of(TrashCanFolderErrorCode.TRASH_CAN_FOLDER_NOT_FOUND, "요청된 TrashCanFolder Id: %d".formatted(trashId)));
	}

	public Optional<TrashCanFolder> findByItemIdAndTableName(Long itemId, FolderType folderType) {
		return trashCanFolderRepository.findByItemIdAndTableName(itemId, folderType);
	}

	public void deleteAllTrashCan(Long projectId) {
		trashCanFolderRepository.deleteAllByProject_Id(projectId);
	}

	public TrashCanFolder save(TrashCanFolder trashCanFolder) {
		return trashCanFolderRepository.save(trashCanFolder);
	}

	public void delete(TrashCanFolder trashCanFolder) {
		trashCanFolderRepository.delete(trashCanFolder);
	}

	public void deleteTrashCanFolderById(Long trashId) {
		trashCanFolderRepository.deleteById(trashId);
	}
}
