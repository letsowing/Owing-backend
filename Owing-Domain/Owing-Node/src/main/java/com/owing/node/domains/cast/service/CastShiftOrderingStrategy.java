package com.owing.node.domains.cast.service;

import com.owing.core.dnd.base.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.projection.CastPositionProjection;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class CastShiftOrderingStrategy extends FileShiftOrderingStrategy<CastNode, CastFolderNode> {

	private final Neo4jTemplate neo4jTemplate;

	public CastShiftOrderingStrategy(
			CastNodeAdapter dndAdapter,
			CastNodeRepository dndRepository,
			Neo4jTemplate neo4jTemplate
	) {
		super(dndAdapter, dndRepository);
		this.neo4jTemplate = neo4jTemplate;
	}

	@Override
	protected CastNode handleEntityUpdate(CastNode entity, CastNode beforeEntity, CastNode afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);

		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
		entity.updatePosition(newPosition);
		CastPositionProjection castPositionProjection = CastPositionProjection.from(entity);
		neo4jTemplate.save(CastNode.class).one(castPositionProjection);
		return entity;
	}
}