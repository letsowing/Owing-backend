package com.owing.entity.domains.universe.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.core.dnd.folder.repository.BaseFolderRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@DomainService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniverseFolderDomainService extends BaseFolderDomainService<UniverseFolder> {

	private final BaseFolderRepository<UniverseFolder> dndRepository;
	private final BaseFolderAdapter<UniverseFolder> dndAdapter;
	private final OrderingStrategy<UniverseFolder> orderingStrategy;

	@Override
	protected BaseDndRepository<UniverseFolder> dndRepository() {
		return dndRepository;
	}

	@Override
	protected BaseDndAdapter<UniverseFolder> dndEntityAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<UniverseFolder> orderingStrategy() {
		return orderingStrategy;
	}
}
