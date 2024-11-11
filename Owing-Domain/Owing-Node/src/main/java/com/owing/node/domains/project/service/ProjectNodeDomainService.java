package com.owing.node.domains.project.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.adaptor.ProjectNodeAdaptor;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectNodeDomainService {

    private final ProjectNodeRepository projectNodeRepository;
    private final ProjectNodeAdaptor projectNodeAdaptor;

    @Transactional
    public void createProjectNode(ProjectNode projectNode) {
        projectNodeRepository.save(projectNode);
    }

    @Transactional
    public void deleteProjectNode(Long projectId) {
        ProjectNode projectNode = projectNodeAdaptor.findById(projectId);
        projectNode.delete();
        projectNodeRepository.save(projectNode);
    }
}
