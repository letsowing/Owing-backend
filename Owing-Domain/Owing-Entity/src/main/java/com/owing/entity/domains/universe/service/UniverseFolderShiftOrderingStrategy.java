package com.owing.entity.domains.universe.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.base.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;

@Component
public class UniverseFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<UniverseFolder> {
	public UniverseFolderShiftOrderingStrategy(
		UniverseFolderAdapter dndAdapter,
		UniverseFolderRepository dndRepository) {
		super(dndAdapter, dndRepository);
	}
}
