package com.owing.node.domains.cast.service;

import com.owing.core.dnd.base.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.stereotype.Component;

@Component
public class CastShiftOrderingStrategy extends FileShiftOrderingStrategy<CastNode, CastFolderNode> {

	public CastShiftOrderingStrategy(CastNodeAdapter dndAdapter, CastNodeRepository dndRepository) {
		super(dndAdapter, dndRepository);
	}

}