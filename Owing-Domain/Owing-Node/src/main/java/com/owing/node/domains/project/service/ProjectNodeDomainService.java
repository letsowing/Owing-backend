package com.owing.node.domains.project.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ProjectNodeDomainService {

    private final ProjectNodeRepository projectNodeRepository;

    public ProjectNode createProjectNode(ProjectNode projectNode) {
        return projectNodeRepository.save(projectNode);
    }
}
