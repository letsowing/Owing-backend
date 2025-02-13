package com.owing.node.domains.cast.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.model.Dnd;
import com.owing.core.dnd.service.shift.FileShiftDndService;
import com.owing.core.dnd.service.shift.adapter.FileShiftAdapter;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CastDndService extends FileShiftDndService<CastNode> {

	private final CastNodeService castNodeDomainService;
	private final CastNodeAdapter castNodeAdapter;

	// =====super() Cast CRUD=====
	@Override
	@Transactional("neo4jTransactionManager")
	public CastNode create(CastNode castNode) {
		long position = getNewPosition(castNode.getParentId());
		castNode.updatePosition(position);

		castNode.updateCoordinate(
			CastConstant.DEFAULT_COORDINATE_X,
			CastConstant.DEFAULT_COORDINATE_Y
		);

		return castNodeAdapter.save(castNode);
	}

	@Override
	@Transactional("neo4jTransactionManager")
	public CastNode updatePosition(CastNode entity, CastNode before, CastNode after, Dnd folder) {
		castNodeDomainService.detachFolder(entity, entity.getParentId());
		super.updatePosition(entity, before, after, folder);
		castNodeDomainService.attachFolder(entity, entity.getParentId());
		return entity;
	}

	@Override
	protected FileShiftAdapter<CastNode> dndAdapter() {
		return castNodeAdapter;
	}
}