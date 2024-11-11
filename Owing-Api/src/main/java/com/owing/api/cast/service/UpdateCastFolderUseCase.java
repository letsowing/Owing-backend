package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastFolderUseCase extends UpdateFolderUseCase<CastFolderNode> {

    private final CastFolderNodeAdaptor castFolderNodeAdaptor;
    private final CastFolderNodeDomainService castFolderNodeDomainService;
    private final MemberUtils memberUtils;
    private final CastFolderNodeMapper castFolderNodeMapper;

    public void executeInfoUpdate(Long folderId, UpdateCastFolderInfo updateCastFolderInfo) {
        CastFolderNode castFolderNode = castFolderNodeAdaptor.findOneById(folderId);
        castFolderNodeDomainService.updateCastFolderNodeInfo(
                castFolderNode,
                updateCastFolderInfo.name(),
                updateCastFolderInfo.description()
        );
    }

    // Bean Setting
    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

    @Override
    protected BaseFolderMapper<CastFolderNode> dndMapper() {
        return this.castFolderNodeMapper;
    }
}
