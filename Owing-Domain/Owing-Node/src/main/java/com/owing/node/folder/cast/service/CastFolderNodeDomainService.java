package com.owing.node.folder.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastFolderNodeDomainService {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final CastFolderNodeAdaptor castFolderNodeAdaptor;

    @Transactional
    public void createCastFolderNode(CastFolderNode castFolderNode, ProjectNode projectNode) {
        castFolderNode.connectProject(projectNode);
        castFolderNodeRepository.save(castFolderNode);
    }

    @Transactional
    public void deleteCastFolderNode(Long castFolderId) {
        CastFolderNode castFolderNode = castFolderNodeAdaptor.findById(castFolderId);
        castFolderNode.delete();
        castFolderNodeRepository.save(castFolderNode);
    }

}
