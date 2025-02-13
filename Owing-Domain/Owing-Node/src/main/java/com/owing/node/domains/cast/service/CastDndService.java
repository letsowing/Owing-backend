package com.owing.node.domains.cast.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.model.DndFolder;
import com.owing.core.dnd.service.shift.DndShiftAdapter;
import com.owing.core.dnd.service.shift.DndShiftService;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CastDndService extends DndShiftService<CastNode> {

	private final CastNodeService castNodeDomainService;
	private final CastNodeAdapter castNodeAdapter;

	// =====super() Cast CRUD=====
	@Override
	@Transactional("neo4jTransactionManager")
	public CastNode create(CastNode castNode) {
		castNode.updateCoordinate(
			CastConstant.DEFAULT_COORDINATE_X,
			CastConstant.DEFAULT_COORDINATE_Y
		);
		return super.create(castNode);
	}

	@Override
	@Transactional("neo4jTransactionManager")
	public CastNode updatePosition(CastNode entity, CastNode before, CastNode after, DndFolder folder) {
		castNodeDomainService.detachFolder(entity, entity.getParentId());
		super.updatePosition(entity, before, after, folder);
		castNodeDomainService.attachFolder(entity, entity.getParentId());
		return entity;
	}

	@Override
	protected DndShiftAdapter<CastNode> dndAdapter() {
		return castNodeAdapter;
	}
}