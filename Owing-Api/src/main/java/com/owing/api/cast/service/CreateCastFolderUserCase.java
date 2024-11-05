package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastFolderRequest;
import com.owing.api.cast.model.mapper.CastFolderNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.project.model.Project;
import com.owing.node.domains.project.adaptor.ProjectNodeAdaptor;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastFolderUserCase {

    private final ProjectNodeAdaptor projectNodeAdaptor;
    private final CastFolderNodeMapper castFolderNodeMapper;
    private final CastFolderNodeDomainService castFolderDomainService;

    public void execute(CreateCastFolderRequest createCastFolderRequest) {
        ProjectNode projectNode = projectNodeAdaptor.findById(createCastFolderRequest.projectId());
        CastFolderNode castFolderNode = castFolderNodeMapper.toEntity(
                createCastFolderRequest.name(),
                createCastFolderRequest.description());
        castFolderDomainService.createCastFolderNode(castFolderNode, projectNode);


    }
}
