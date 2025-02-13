package com.owing.entity.domains.trashcan.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;
import com.owing.entity.domains.trashcan.repository.TrashCanFolderRepository;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrashCanDomainService {
	private final TrashCanRepository trashCanRepository;
	private final TrashCanFolderRepository trashCanFolderRepository;
	private final TrashCanAdaptor trashCanAdaptor;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final ProjectAdapter projectAdapter;

	@Transactional("jpaTransactionManager")
	public void deleteTrashCan(Long trashId) {
		TrashCan trashCan = trashCanAdaptor.findById(trashId);
		TrashCanFolder folder = trashCan.getTrashCanFolder();
		folder.getTrashCanList().remove(trashCan);
		trashCanRepository.delete(trashCan);
		if (folder.getTrashCanList().isEmpty()) {
			trashCanFolderRepository.delete(folder);
		}
	}

	private TrashCanFolder getOrCreateTrashCanFolder(DndFolder folder) {
		FolderType folderType = FolderType.fromClassName(folder);
		return trashCanFolderAdaptor.findByItemIdAndTableName(folder.getId(), folderType)
			.orElseGet(() -> {
				Project project = projectAdapter.findById(folder.getProjectId());
				return trashCanFolderRepository.save(TrashCanFolder.fromFolder(folder, project));
			});
	}

	@Transactional("jpaTransactionManager")
	public void trash(DndFile file) {
		TrashCanFolder trashCanFolder = getOrCreateTrashCanFolder(file.getFolder());
		TrashCan trashCan = TrashCan.fromFile(file, trashCanFolder);
		trashCanRepository.save(trashCan);

		trashCanFolderRepository.updateCreatedAtById(trashCan.getTrashCanFolder().getId()); //fixme
	}

	@Transactional("jpaTransactionManager")
	public void trash(DndFolder folder) {
		TrashCanFolder trashCanFolder = getOrCreateTrashCanFolder(folder);

		List<TrashCan> trashCanList = folder.getFiles().stream()
			.map(file -> TrashCan.fromFile(file, trashCanFolder))
			.toList();

		trashCanFolder.addTrashCan(trashCanList);
	}

}
