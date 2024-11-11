package com.owing.node.folder.cast.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeNotFoundException;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class CastFolderNodeAdapter extends BaseFolderAdapter<CastFolderNode> {

    private final CastFolderNodeRepository castFolderNodeRepository;

    public CastFolderNode findOneById(Long castFolderNodeId) {
        return castFolderNodeRepository.findOneById(castFolderNodeId)
                .orElseThrow(() -> CastFolderNodeNotFoundException.of(
                        CastFolderNodeErrorCode.NODE_NOT_FOUND,
                        "cast folder id: %d".formatted(castFolderNodeId)
                ));
    }

    public CastFolderNode findOneWithRelationshipById(Long castFolderNodeId) {
        return castFolderNodeRepository.findOneWithRelationshipById(castFolderNodeId)
                .orElseThrow(() -> CastFolderNodeNotFoundException.of(
                        CastFolderNodeErrorCode.NODE_NOT_FOUND,
                        "cast folder id: %d".formatted(castFolderNodeId)
                ));
    }

    public List<CastFolderNode> findAllWithRelationshipByProjectId(Long projectId) {
        return castFolderNodeRepository.findAllWithRelationshipByProjectId(projectId);
    }

    @Override
    protected BaseDndRepository<CastFolderNode> dndRepository() {
        return castFolderNodeRepository;
    }
}
