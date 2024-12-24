package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.core.dnd.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniverseFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<UniverseFolder> {
	private final UniverseFolderAdapter dndAdapter;

	@Override
	protected DndFolderAdapter<UniverseFolder> dndAdapter() {
		return dndAdapter;
	}
}
