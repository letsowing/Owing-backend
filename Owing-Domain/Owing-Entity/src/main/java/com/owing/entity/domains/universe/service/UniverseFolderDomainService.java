package com.owing.entity.domains.universe.service;

import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseFolderRepository;
import com.owing.entity.domains.universe.repository.UniverseRepository;
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

import java.util.List;

@DomainService
@Transactional(readOnly = true, transactionManager = "jpaTransactionManager")
@RequiredArgsConstructor
public class UniverseFolderDomainService extends BaseFolderDomainService<UniverseFolder> {

	private final BaseFolderRepository<UniverseFolder> dndRepository;
	private final BaseFolderAdapter<UniverseFolder> dndAdapter;
	private final OrderingStrategy<UniverseFolder> orderingStrategy;
	private final UniverseFolderRepository universeFolderRepository;
	private final UniverseRepository universeRepository;

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

	@Transactional("jpaTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		universeFolderRepository.restoreById(folderItemId);
		trashCanItemIds
				.forEach(universeRepository::restoreById);
	}
}
