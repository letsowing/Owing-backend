package com.owing.entity.folders.trashcan.adaptor;

import java.util.List;

import com.owing.common.annotation.Adaptor;
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

}
