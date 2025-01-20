package com.owing.api.cast.service.folder;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadCastFolderUseCase extends ReadFolderUseCase<CastFolderNode> {

    private final ProjectNodeAdapter projectNodeAdapter;
    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderNodeMapper castFolderNodeMapper;

    @Override
    public FolderInfoResponse executeRetrieve(Long folderId) {
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
        return castFolderNodeMapper.toInfoResponse(castFolderNode);
    }

    @Override
    public FolderInfoListResponse executeList(Long projectId) {
        ProjectNode projectNode = projectNodeAdapter.findById(projectId);
        List<CastFolderNode> castFolderNodeList = castFolderNodeAdapter.findAllWithRelationshipByProjectId(projectNode.getId());
        return castFolderNodeMapper.toListResponse(castFolderNodeList);
    }

    public List<CastFolderDropdownItemResponse> executeDropdownList(Long projectId) {
        ProjectNode projectNode = projectNodeAdapter.findById(projectId);
        List<CastFolderNode> castFolderNodeList = castFolderNodeAdapter.findAllByParentId(projectNode.getId());
        return castFolderNodeMapper.toDropdownListResponse(castFolderNodeList);
    }

    @Override
    protected DndAdapter<CastFolderNode> folderAdapter() {
        return castFolderNodeAdapter;
    }

    // Bean Setting
    @Override
    protected BaseFolderMapper<CastFolderNode> folderMapper() {
        return this.castFolderNodeMapper;
    }

}
