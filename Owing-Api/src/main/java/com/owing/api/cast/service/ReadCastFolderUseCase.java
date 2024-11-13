package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastFolderDropdownItemResponse;
import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadCastFolderUseCase extends ReadFolderUseCase<CastFolderNode> {

    private final ProjectNodeAdapter projectNodeAdapter;
    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderNodeMapper castFolderNodeMapper;
    private final CastFolderNodeDomainService castFolderNodeDomainService;

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

    // Bean Setting
    @Override
    protected BaseFolderMapper<CastFolderNode> dndMapper() {
        return this.castFolderNodeMapper;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

}
