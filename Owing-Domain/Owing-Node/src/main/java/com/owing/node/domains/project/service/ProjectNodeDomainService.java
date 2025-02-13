package com.owing.node.domains.project.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ProjectNodeDomainService {

    private final ProjectNodeRepository projectNodeRepository;
    private final ProjectNodeAdapter projectNodeAdapter;
    private final CastFolderNodeRepository castFolderNodeRepository;

    @Transactional("neo4jTransactionManager")
    public ProjectNode createProjectNode(ProjectNode projectNode) {
        projectNode = projectNodeRepository.save(projectNode);
        CastFolderNode initialCastFolder = CastFolderNode.init(projectNode);
        castFolderNodeRepository.save(initialCastFolder);
        return projectNode;
    }

    @Transactional("neo4jTransactionManager")
    public void deleteProjectNode(Long projectId) {
        ProjectNode projectNode = projectNodeAdapter.findById(projectId);
        projectNode.delete();
        projectNodeRepository.save(projectNode);
    }
}
