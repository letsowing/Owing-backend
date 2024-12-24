package com.owing.entity.domains.universe.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.folder.service.DndFolderDomainService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
@RequiredArgsConstructor
public class UniverseFolderDomainService extends DndFolderDomainService<UniverseFolder> {

	private final UniverseFolderAdapter dndAdapter;
	private final UniverseAdapter fileAdapter;
	private final OrderingStrategy<UniverseFolder> orderingStrategy;

	@Override
	protected DndAdapter<UniverseFolder> dndAdapter() {
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
