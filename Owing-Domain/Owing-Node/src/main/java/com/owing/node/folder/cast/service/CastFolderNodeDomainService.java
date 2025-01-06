package com.owing.node.folder.cast.service;

import java.util.List;

import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderDeleteProjection;
import com.owing.node.folder.cast.model.projection.CastFolderInfoProjection;
import com.owing.node.folder.cast.model.projection.CastFolderTitleProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "neo4jTransactionManager")
public class CastFolderNodeDomainService extends BaseFolderDomainService<CastFolderNode> {

    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderShiftOrderingStrategy castFolderShiftOrderingStrategy;
    private final CastFolderNodeRepository castFolderNodeRepository;

    private final CastNodeRepository castNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    @Transactional("neo4jTransactionManager")
    public void updateCastFolderNodeInfo(CastFolderNode castFolderNode, String name, String description) {
        castFolderNode.updateTitle(name);
        castFolderNode.updateDescription(description);

        CastFolderInfoProjection castFolderInfoProjection = CastFolderInfoProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderInfoProjection);
    }

    @Transactional("neo4jTransactionManager")
    public void restore(Long folderItemId, List<Long> trashCanItemIds) {
        castFolderNodeRepository.restoreById(folderItemId);
        trashCanItemIds
                .forEach(castNodeRepository::restoreById);
    }

    // =====super() CastFolder CRUD=====
    @Override
    public CastFolderNode getEntity(Long folderId) {
        return castFolderNodeAdapter.findOneById(folderId);
    }

    @Override
    @Transactional("neo4jTransactionManager")
    public void deleteEntity(CastFolderNode entity) {
        castFolderNodeRepository.deleteFolderById(entity.getId());
        orderingStrategy().reorderEntity(entity);
        entity.delete();
        CastFolderDeleteProjection deleteProjection = CastFolderDeleteProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(deleteProjection);
    }

    @Override
    @Transactional("neo4jTransactionManager")
    public CastFolderNode updateName(CastFolderNode entity, CastFolderNode newEntity) {
        entity.updateTitle(newEntity.getName());
        CastFolderTitleProjection titleProjection = CastFolderTitleProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(titleProjection);
        return entity;
    }

    // Bean Setting
    @Override
    protected BaseDndRepository<CastFolderNode> dndRepository() {
        return this.castFolderNodeRepository;
    }

    @Override
    protected BaseDndAdapter<CastFolderNode> dndEntityAdapter() {
        return this.castFolderNodeAdapter;
    }

    @Override
    protected OrderingStrategy<CastFolderNode> orderingStrategy() {
        return this.castFolderShiftOrderingStrategy;
    }

}
