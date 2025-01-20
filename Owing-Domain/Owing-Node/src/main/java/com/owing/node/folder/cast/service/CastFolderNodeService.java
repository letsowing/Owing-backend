package com.owing.node.folder.cast.service;

import java.util.List;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderInfoProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastFolderNodeService extends DndService<CastFolderNode> {

    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderShiftOrderingStrategy castFolderShiftOrderingStrategy;
    private final CastFolderNodeRepository castFolderNodeRepository;

    private final CastNodeRepository castNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    @Transactional
    public void updateCastFolderNodeInfo(CastFolderNode castFolderNode, String name, String description) {
        castFolderNode.updateName(name);
        castFolderNode.updateDescription(description);

        CastFolderInfoProjection castFolderInfoProjection = CastFolderInfoProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderInfoProjection);
    }

    @Transactional
    public void restore(Long folderItemId, List<Long> trashCanItemIds) {
        castFolderNodeRepository.restoreById(folderItemId);
        trashCanItemIds
                .forEach(castNodeRepository::restoreById);
    }

    // Bean Setting
    @Override
    protected DndAdapter<CastFolderNode> dndAdapter() {
        return this.castFolderNodeAdapter;
    }

    @Override
    protected OrderingStrategy<CastFolderNode> orderingStrategy() {
        return this.castFolderShiftOrderingStrategy;
    }

}
