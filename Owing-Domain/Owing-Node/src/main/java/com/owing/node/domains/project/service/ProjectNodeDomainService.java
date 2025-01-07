package com.owing.node.domains.project.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.adapter.ProjectNodeAdapter;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "neo4jTransactionManager")
public class ProjectNodeDomainService {

    private final ProjectNodeRepository projectNodeRepository;
    private final ProjectNodeAdapter projectNodeAdapter;

    @Transactional("neo4jTransactionManager")
    public ProjectNode createProjectNode(ProjectNode projectNode) {
        return projectNodeRepository.save(projectNode);
    }

    @Transactional("neo4jTransactionManager")
    public void deleteProjectNode(Long projectId) {
        ProjectNode projectNode = projectNodeAdapter.findById(projectId);
        projectNode.delete();
        projectNodeRepository.save(projectNode);
    }
}
