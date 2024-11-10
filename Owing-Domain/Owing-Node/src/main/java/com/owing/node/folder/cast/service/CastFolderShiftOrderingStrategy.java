package com.owing.node.folder.cast.service;

import com.owing.core.dnd.base.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import org.springframework.stereotype.Component;

@Component
public class CastFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<CastFolderNode> {
	public CastFolderShiftOrderingStrategy(
			CastFolderNodeAdaptor dndAdapter,
			CastFolderNodeRepository dndRepository
	) {
		super(dndAdapter, dndRepository);
	}
}
