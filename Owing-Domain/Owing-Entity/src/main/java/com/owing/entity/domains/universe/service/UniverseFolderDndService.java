package com.owing.entity.domains.universe.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.service.shift.DndShiftAdapter;
import com.owing.core.dnd.service.shift.DndShiftService;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseFolderDndService extends DndShiftService<UniverseFolder> {
	private final UniverseFolderAdapter dndAdapter;
	private final UniverseAdapter fileAdapter;

	@Transactional("jpaTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		dndAdapter.restoreById(folderItemId);
		trashCanItemIds.forEach(fileAdapter::restoreById);
	}

	@Override
	protected DndShiftAdapter<UniverseFolder> dndAdapter() {
		return dndAdapter;
	}
}
