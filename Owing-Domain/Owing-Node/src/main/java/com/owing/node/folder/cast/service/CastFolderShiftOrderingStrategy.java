package com.owing.node.folder.cast.service;

import com.owing.core.dnd.base.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.core.dnd.folder.repository.BaseFolderRepository;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class CastFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<CastFolderNode> {
	public CastFolderShiftOrderingStrategy(BaseFolderAdapter<CastFolderNode> dndAdapter, BaseFolderRepository<CastFolderNode> dndRepository) {
		super(dndAdapter, dndRepository);
	}
}
