package com.owing.entity.domains.universe.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.DndRepository;
import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseFolderAdapter extends DndFolderAdapter<UniverseFolder> {

	private final UniverseFolderRepository universeFolderRepository;

	@Override
	protected DndRepository<UniverseFolder> dndRepository() {
		return universeFolderRepository;
	}

	public void restoreById(Long folderItemId) {
		universeFolderRepository.restoreById(folderItemId);
	}
}
