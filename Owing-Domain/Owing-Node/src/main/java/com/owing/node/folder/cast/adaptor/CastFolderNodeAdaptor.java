package com.owing.node.folder.cast.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeNotFoundException;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class CastFolderNodeAdaptor {

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

    public List<CastFolderNode> findOneAllWithRelationshipByProjectId(Long projectId) {
        return castFolderNodeRepository.findOneAllWithRelationshipByProjectId(projectId);
    }
}
