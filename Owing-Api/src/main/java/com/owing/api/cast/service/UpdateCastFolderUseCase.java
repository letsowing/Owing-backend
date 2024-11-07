package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.common.annotation.UseCase;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastFolderUseCase {

    private final CastFolderNodeAdaptor castFolderNodeAdaptor;
    private final CastFolderNodeDomainService castFolderNodeDomainService;

    public void executeInfoUpdate(Long folderId, UpdateCastFolderInfo updateCastFolderInfo) {
        CastFolderNode castFolderNode = castFolderNodeAdaptor.findOneById(folderId);
        castFolderNodeDomainService.updateCastFolderNodeInfo(
                castFolderNode,
                updateCastFolderInfo.name(),
                updateCastFolderInfo.description()
        );
    }
}
