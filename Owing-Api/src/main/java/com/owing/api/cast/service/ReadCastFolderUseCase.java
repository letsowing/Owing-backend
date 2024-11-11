package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastFolderResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.cast.model.mapper.CastNodeMapper;
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
    private final CastNodeMapper castNodeMapper;
    private final CastFolderNodeMapper castFolderNodeMapper;
    private final CastFolderNodeDomainService castFolderNodeDomainService;

    @Deprecated
    public List<CastFolderResponse> executeGetList(Long projectId) {
        ProjectNode projectNode = projectNodeAdapter.findById(projectId);
        List<CastFolderNode> castFolderNodeList = castFolderNodeAdapter.findAllWithRelationshipByProjectId(projectNode.getId());
        return getSortedCastFolderResponse(castFolderNodeList);
    }

    @Deprecated(forRemoval = true)
    public CastFolderResponse executeGetOne(Long folderId) {
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneWithRelationshipById(folderId);
        return getSortedCastFolderResponse(List.of(castFolderNode)).getFirst();
    }

    @Deprecated
    private List<CastFolderResponse> getSortedCastFolderResponse(List<CastFolderNode> castFolderNodeList) {
        return castFolderNodeList.stream()
                .map(folder -> castFolderNodeMapper.toFolderResponse(
                        folder,
                        folder.getCast().stream()
                                .map(castNodeMapper::toFileResponse)
                                .toList()
                ))
                .toList();
    }

    @Override
    protected BaseFolderMapper<CastFolderNode> dndMapper() {
        return this.castFolderNodeMapper;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

}
