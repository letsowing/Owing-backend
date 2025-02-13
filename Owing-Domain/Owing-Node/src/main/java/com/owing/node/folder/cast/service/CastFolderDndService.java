package com.owing.node.folder.cast.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.service.shift.FolderShiftDndService;
import com.owing.core.dnd.service.shift.adapter.FolderShiftAdapter;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CastFolderDndService extends FolderShiftDndService<CastFolderNode> {

	private final CastFolderNodeAdapter adapter;
	private final CastFolderNodeRepository castFolderNodeRepository;
	private final CastNodeRepository castNodeRepository;

	@Transactional("neo4jTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		castFolderNodeRepository.restoreById(folderItemId);
		trashCanItemIds
			.forEach(castNodeRepository::restoreById);
	}

	@Override
	protected FolderShiftAdapter<CastFolderNode> dndAdapter() {
		return adapter;
	}
}
