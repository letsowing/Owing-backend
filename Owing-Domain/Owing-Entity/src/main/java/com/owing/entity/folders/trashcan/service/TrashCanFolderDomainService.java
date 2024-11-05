package com.owing.entity.folders.trashcan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrashCanFolderDomainService {

	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final TrashCanFolderRepository trashCanFolderRepository;

	public List<TrashCanFolder> getTrashCanFolder(Long projectId) {
		return trashCanFolderAdaptor.findAllByProject_Id(projectId);
	}

	public void deleteAllTrashCan(Long projectId) {
		trashCanFolderRepository.deleteAllByProject_Id(projectId);
	}

	public void deleteTrashCanFolder(Long trashId) {
		trashCanFolderRepository.deleteById(trashId);
	}

	public void restoreTrashCan(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findById(trashId);
		// todo trashId에 관련되어 있는 폴더 복원 작업부터 하기

		// todo trashCan 에 Cast에 해당하는 deleted 부분을 false로 변경
		if (trashCanFolder.getTableName().isCast()){
			// todo cast 하위에 있던 file들 모두 복원 처리하기.
			trashCanFolderUnderFileRestoreCast(trashCanFolder);
		}
		// todo trashCan 에 Universe에 해당하는 deleted 부분을 false로 변경
		else if (trashCanFolder.getTableName().isUniverse()){
			// todo universe 하위에 있던 file들 모두 복원 처리하기.
			trashCanFolderUnderFileRestoreUniverse(trashCanFolder);
		}
		// todo trashCan 에 Story에 해당하는 deleted 부분을 false로 변경
		else if (trashCanFolder.getTableName().isStory()){
			// todo story 하위에 있던 file들 모두 복원 처리하기.
			trashCanFolderUnderFileRestoreStory(trashCanFolder);
		}

		trashCanFolderRepository.deleteById(trashId);
	}

	private void trashCanFolderUnderFileRestoreCast(TrashCanFolder trashCanFolder){
		List<TrashCan> trashCans = trashCanFolder.getTrashCanList();

		for (TrashCan trashCan : trashCans){
			// todo 여기서 cast 에서 itemId에 해당하는 거 복원하기
			trashCan.getItemId();
		}
	}

	private void trashCanFolderUnderFileRestoreUniverse(TrashCanFolder trashCanFolder){
		List<TrashCan> trashCans = trashCanFolder.getTrashCanList();

		for (TrashCan trashCan : trashCans){
			// todo 여기서 universe 에서 itemId에 해당하는 거 복원하기
			trashCan.getItemId();
		}
	}

	private void trashCanFolderUnderFileRestoreStory(TrashCanFolder trashCanFolder){
		List<TrashCan> trashCans = trashCanFolder.getTrashCanList();

		for (TrashCan trashCan : trashCans){
			// todo 여기서 story 에서 itemId에 해당하는 거 복원하기
			trashCan.getItemId();
		}
	}
}
