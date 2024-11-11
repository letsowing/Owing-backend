package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.model.dto.response.CastFolderInfoResponse;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.domains.project.adaptor.ProjectNodeAdaptor;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastFolderUseCase extends CreateFolderUseCase<CastFolderNode> {

    private final ProjectNodeAdaptor projectNodeAdaptor;
    private final CastFolderNodeMapper castFolderNodeMapper;
    private final CastFolderNodeDomainService castFolderDomainService;
    private final MemberUtils memberUtils;

    @Override
    public FolderInfoResponse execute(AddFolderRequest createCastFolderRequest) {
        ProjectNode projectNode = projectNodeAdaptor.findById(createCastFolderRequest.projectId());
        CastFolderNode castFolderNode = castFolderNodeMapper.toEntity(
                createCastFolderRequest.name(),
                createCastFolderRequest.description());

        castFolderNode.connectProject(projectNode);
        CastFolderNode savedCastFolder = castFolderDomainService.createEntity(castFolderNode);

        return castFolderNodeMapper.toInfoResponse(savedCastFolder);
    }

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderDomainService;
    }

    @Override
    protected BaseFolderMapper<CastFolderNode> dndMapper() {
        return this.castFolderNodeMapper;
    }
}
