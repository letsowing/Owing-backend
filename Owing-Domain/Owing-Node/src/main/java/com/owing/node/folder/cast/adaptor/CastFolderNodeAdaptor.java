package com.owing.node.folder.cast.adaptor;

import com.owing.common.annotation.Adaptor;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeNotFoundException;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CastFolderNodeAdaptor {

    private final CastFolderNodeRepository castFolderNodeRepository;

    public CastFolderNode findById(Long castFolderNodeId) {
        return castFolderNodeRepository.findById(castFolderNodeId)
                .orElseThrow(() -> CastFolderNodeNotFoundException.of(
                        CastFolderNodeErrorCode.NODE_NOT_FOUND,
                        "cast folder id: %d".formatted(castFolderNodeId)
                ));
    }
}
