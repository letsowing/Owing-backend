package com.owing.entity.domains.trashcan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrashCanDomainService {
	private final TrashCanFolderRepository trashCanRepository;
	private final TrashCanAdaptor trashCanAdaptor;

	public void deleteTrashCan(Long trashId) {
		trashCanRepository.deleteById(trashId);
	}

	public void restoreTrashCan(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);

		// todo trashCan 에 Cast에 해당하는 deleted 부분을 false로 변경
		if (trashCan.getTrashCanFolder().getTableName().isCast()){

		}
		// todo trashCan 에 Universe에 해당하는 deleted 부분을 false로 변경
		else if (trashCan.getTrashCanFolder().getTableName().isUniverse()){

		}
		// todo trashCan 에 Story에 해당하는 deleted 부분을 false로 변경
		else if (trashCan.getTrashCanFolder().getTableName().isStory()){

		}
		trashCanRepository.deleteById(trashId);
	}
}
