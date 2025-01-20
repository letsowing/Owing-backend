package com.owing.entity.domains.universe.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.orderStrategy.shift.adapter.FolderShiftAdapter;
import com.owing.core.dnd.orderStrategy.shift.repository.DndShiftRepository;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class UniverseFolderAdapter extends FolderShiftAdapter<UniverseFolder> {

	private final UniverseFolderRepository universeFolderRepository;

	@Override
	protected DndShiftRepository<UniverseFolder> dndRepository() {
		return universeFolderRepository;
	}

	public void restoreById(Long folderItemId) {
		universeFolderRepository.restoreById(folderItemId);
	}
}
