package com.owing.api.cast.service;

import com.owing.common.annotation.UseCase;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastFolderUseCase {

    private final CastFolderNodeDomainService castFolderNodeDomainService;
    private final CastFolderNodeAdaptor castFolderNodeAdaptor;

    public void execute(Long folderId) {
        CastFolderNode castFolderNode = castFolderNodeAdaptor.findOneById(folderId);
        castFolderNodeDomainService.deleteCastFolderNode(castFolderNode);
    }
}
