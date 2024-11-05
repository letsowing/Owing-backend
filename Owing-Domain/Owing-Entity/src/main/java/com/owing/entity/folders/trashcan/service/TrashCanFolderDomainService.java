package com.owing.entity.folders.trashcan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrashCanFolderDomainService {

	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public List<TrashCanFolder> getTrashCanFolder(Long projectId) {
		return trashCanFolderAdaptor.findAllByProject_Id(projectId);
	}
}
