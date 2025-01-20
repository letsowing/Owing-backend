package com.owing.api.cast.service.folder;

import com.owing.api.cast.model.dto.request.UpdateCastFolderInfo;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateCastFolderUseCase extends UpdateFolderUseCase<CastFolderNode> {

    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderNodeService castFolderNodeDomainService;
    private final MemberUtils memberUtils;
    private final CastFolderNodeMapper castFolderNodeMapper;

    public void executeInfoUpdate(Long folderId, UpdateCastFolderInfo updateCastFolderInfo) {
        CastFolderNode castFolderNode = castFolderNodeAdapter.findById(folderId);
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
    protected DndService<CastFolderNode> folderService() {
        return castFolderNodeDomainService;
    }

    @Override
    protected BaseFolderMapper<CastFolderNode> folderMapper() {
        return castFolderNodeMapper;
    }

    @Override
    protected DndAdapter<CastFolderNode> folderAdapter() {
        return castFolderNodeAdapter;
    }

}
