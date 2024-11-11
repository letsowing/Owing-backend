package com.owing.node.domains.project.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.node.domains.project.error.code.ProjectNodeErrorCode;
import com.owing.node.domains.project.error.exception.ProjectNodeNotFoundException;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.domains.project.repository.ProjectNodeRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class ProjectNodeAdapter {

    private final ProjectNodeRepository projectNodeRepository;

    public ProjectNode findById(Long projectId) {
        return projectNodeRepository.findById(projectId)
                .orElseThrow(() -> ProjectNodeNotFoundException.of(ProjectNodeErrorCode.PROJECT_NODE_NOT_FOUND, "Project Node Id: %d".formatted(projectId)));
    }
}
