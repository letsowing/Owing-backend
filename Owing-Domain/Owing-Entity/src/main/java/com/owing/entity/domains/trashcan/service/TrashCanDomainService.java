package com.owing.entity.domains.trashcan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrashCanDomainService {
	private final TrashCanRepository trashCanRepository;
	private final TrashCanFolderRepository trashCanFolderRepository;
	private final StoryRepository storyRepository;
	private final UniverseRepository universeRepository;
	//private final CastRepository CastRepository;
	private final TrashCanAdaptor trashCanAdaptor;
	private final TrashCanFolderDomainService trashCanFolderDomainService;

	public void deleteTrashCan(Long trashId) {
		trashCanRepository.deleteById(trashId);
	}

	public void restoreTrashCan(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);

		// todo trashCan 에 Cast에 해당하는 deleted 부분을 false로 변경
		if (trashCan.getTrashCanFolder().getTableName().isCast()){

		}
		else if (trashCan.getTrashCanFolder().getTableName().isUniverse()){
			universeRepository.restoreById(trashCan.getItemId());
		}
		else if (trashCan.getTrashCanFolder().getTableName().isStory()){
			storyRepository.restoreById(trashCan.getItemId());
		}

		trashCanRepository.deleteById(trashId);
		if (trashCan.getTrashCanFolder().getTrashCanList().isEmpty()){
			trashCanFolderRepository.deleteById(trashCan.getTrashCanFolder().getId());
		}
	}

	public void createTrashCan(TrashCan trashCan) {
		trashCanRepository.save(trashCan);
		trashCanFolderRepository.updateCreatedAtById(trashCan.getTrashCanFolder().getId());
	}
}
