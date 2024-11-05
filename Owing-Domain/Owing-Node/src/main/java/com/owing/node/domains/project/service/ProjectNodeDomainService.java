package com.owing.node.domains.project.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectNodeDomainService {

    private final ProjectNodeRepository projectNodeRepository;

    @Transactional
    public void createProjectNode(ProjectNode projectNode) {
        projectNodeRepository.save(projectNode);
    }

    @Transactional
    public void deleteProjectNode(ProjectNode projectNode) {
        projectNodeRepository.delete(projectNode);
    }
}
