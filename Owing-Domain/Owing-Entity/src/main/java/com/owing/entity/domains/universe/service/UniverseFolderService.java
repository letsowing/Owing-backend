package com.owing.entity.domains.universe.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.orderStrategy.shift.adapter.FolderShiftAdapter;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
@RequiredArgsConstructor
public class UniverseFolderService extends DndService<UniverseFolder> {

	private final UniverseFolderAdapter dndAdapter;
	private final UniverseAdapter fileAdapter;
	private final OrderingStrategy<UniverseFolder> orderingStrategy;

	@Override
	protected FolderShiftAdapter<UniverseFolder> dndAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<UniverseFolder> orderingStrategy() {
		return orderingStrategy;
	}

	@Transactional("jpaTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		dndAdapter.restoreById(folderItemId);
		trashCanItemIds.forEach(fileAdapter::restoreById);
	}
}
