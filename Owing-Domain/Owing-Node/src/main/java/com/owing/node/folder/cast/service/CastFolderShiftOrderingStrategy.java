package com.owing.node.folder.cast.service;

import com.owing.core.dnd.base.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class CastFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<CastFolderNode> {
	private final Neo4jTemplate neo4jTemplate;

	public CastFolderShiftOrderingStrategy(
			CastFolderNodeAdaptor dndAdapter,
			CastFolderNodeRepository dndRepository,
			Neo4jTemplate neo4jTemplate) {
		super(dndAdapter, dndRepository);
		this.neo4jTemplate = neo4jTemplate;
	}

	@Override
	protected CastFolderNode handleEntityUpdate(CastFolderNode entity, CastFolderNode beforeEntity, CastFolderNode afterEntity) {
		long newPosition = getUpdatePosition(entity, beforeEntity, afterEntity);

		if (entity.getPosition() < newPosition) {
			moveFolderDown(newPosition, entity.getPosition(), entity.getParentId());
		} else {
			moveFolderUp(newPosition, entity.getPosition(), entity.getParentId());
		}
		entity.updatePosition(newPosition);
		CastFolderPositionProjection castFolderPositionProjection = CastFolderPositionProjection.from(entity);
		neo4jTemplate.save(CastFolderNode.class).one(castFolderPositionProjection);
		return entity;
	}
}
